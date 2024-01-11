package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.sage.codex.sagecodex.config.ProjectGlobalConfig;
import com.sage.codex.sagecodex.enums.CodeEnvEnum;
import com.sage.codex.sagecodex.enums.CodePurposeEnum;
import com.sage.codex.sagecodex.handler.SageCodeXDocumentListener;
import com.sage.codex.sagecodex.model.DocumentContext;
import com.sage.codex.sagecodex.model.GenerationReq;
import com.sage.codex.sagecodex.model.PageContext;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.theme.SageCodeXManage;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;


/**
 * @Description： 根据选中的代码生成注释
 * @Author: xionghao
 * @Date: 2023/11/21 14:58
 */
public class CommentAddAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (SageConfigurationSetting.checkServerUrlIsEmpty(event.getProject())) {
            // 未设置服务地址，直接返回
            return;
        }
        Project project = event.getProject();
        DataContext dataContext = event.getDataContext();
        // context能够也获取到其余信息, 入参为 PlatformDataKeys 定义的字段
        Editor editor = dataContext.getData(PlatformDataKeys.EDITOR);

        SageCodeXManage.checkAndRemoveListener(editor);

        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            ApplicationManager.getApplication().invokeLater(() -> {

                // 获取选择信息，Caret是一种文本表示方法
                Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
                int start = primaryCaret.getSelectionStart();
                int end = primaryCaret.getSelectionEnd();

                DocumentContext documentContext = generateDocumentContext(editor);
                // 如果用户没有选中文本，则返回
                if (StringUtils.isEmpty(documentContext.getCaretBeforeText())) {
                    SageCodeXManage.checkAndAddDocumentListener(editor);
                    return;
                }
                PageContext pageContext = buildPageContext(documentContext, editor);

                String dialogueId = UUID.randomUUID().toString().replace("-", "");
                String chatId = UserInfoConfigurationSetting.settings().chatId;
                ApplicationManager.getApplication().executeOnPooledThread(() -> {
                    SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
                    GenerationReq req = applicationService.buildGenerationReq(chatId, dialogueId, documentContext, pageContext, CodeEnvEnum.REPO_WS);
                    String response = applicationService.doHttpPost(editor.getProject(), req, true);
                    System.out.println("----response:" + response);
                    if (StringUtils.isNotBlank(response)) {
                        Document document = editor.getDocument();
                        // 替换鼠标选择的文本内容为value变量的值
                        WriteCommandAction.runWriteCommandAction(project, () -> {

                            document.replaceString(start, end, response);
                            // 移除选择操作
                            primaryCaret.removeSelection();
                            // 注册文档监听器
                            System.out.println("----注册文档监听器");
                            SageCodeXManage.checkAndAddDocumentListener(editor);
                        });
                    }
                });
            });
        });
    }


    private DocumentContext generateDocumentContext(Editor editor) {
        Document document = editor.getDocument();
        DocumentContext context = new DocumentContext();
        // 选中的代码
        String selectedText = editor.getSelectionModel().getSelectedText();
        context.setCaretBeforeText(selectedText);
        context.setCodePurposeEnum(CodePurposeEnum.COMMENT_ADD);
        context.setDocumentText(document.getText());
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());
        String fileTypeName = psiFile.getFileType().getName();
        context.setFileTypeName(fileTypeName);
        return context;
    }


    private PageContext buildPageContext(DocumentContext documentContext, Editor editor) {
        PageContext pageContext = new PageContext();
        pageContext.setPageContext(documentContext.getDocumentText());
        pageContext.setLineNumber(documentContext.getLineNumber());

        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());
        String name = psiFile.getVirtualFile().getName();
        pageContext.setFileName(name);
        return pageContext;
    }

}
