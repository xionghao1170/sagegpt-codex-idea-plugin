package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.sage.codex.sagecodex.config.ProjectGlobalConfig;
import com.sage.codex.sagecodex.enums.CodeEnvEnum;
import com.sage.codex.sagecodex.enums.CodePurposeEnum;
import com.sage.codex.sagecodex.handler.SageCodeXCaretListener;
import com.sage.codex.sagecodex.handler.SageCodeXDocumentListener;
import com.sage.codex.sagecodex.handler.SageCodeXEditorTabListener;
import com.sage.codex.sagecodex.listener.SageCodeXCustomElementRenderer;
import com.sage.codex.sagecodex.model.DocumentContext;
import com.sage.codex.sagecodex.model.GenerationReq;
import com.sage.codex.sagecodex.model.PageContext;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.theme.SageCodeXManage;
import com.sage.codex.sagecodex.utils.RenderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Description： 根据选中的文本生成代码
 * @Author: xionghao
 * @Date: 2023/11/21 16:21
 */
public class GenerationCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (SageConfigurationSetting.checkServerUrlIsEmpty(event.getProject())) {
            // 未设置服务地址，直接返回
            return;
        }
        DataContext dataContext = event.getDataContext();
        // context能够也获取到其余信息, 入参为 PlatformDataKeys 定义的字段
        Editor editor = dataContext.getData(PlatformDataKeys.EDITOR);
        SageCodeXManage.checkAndRemoveListener(editor);
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            ApplicationManager.getApplication().invokeLater(() -> {
                // context能够也获取到其余信息, 入参为 PlatformDataKeys 定义的字段
                SelectionModel selectionModel = editor.getSelectionModel();
                int offset = selectionModel.getSelectionEnd(); // 获取选中文本的末尾偏移量
                int lineNumber = editor.getDocument().getLineNumber(offset); // 获取选中文本的行号
                int lineStartOffset = editor.getDocument().getLineStartOffset(lineNumber + 1); // 获取选中文本的起始偏移量

                DocumentContext documentContext = generateDocumentContext(editor);
                // 如果用户没有选中文本，则返回
                if (StringUtils.isEmpty(documentContext.getCaretBeforeText())) {
                    SageCodeXManage.checkAndAddDocumentListener(editor);
                    return;
                }
                PageContext pageContext = buildPageContext(documentContext, editor);

                String dialogueId = UUID.randomUUID().toString().replace("-", "");
                String chatId = UserInfoConfigurationSetting.settings().chatId;
                SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
                GenerationReq req = applicationService.buildGenerationReq(chatId, dialogueId, documentContext, pageContext, CodeEnvEnum.REPO_WS);
                String response = applicationService.doHttpPost(editor.getProject(), req, true);
                System.out.println("----response:" + response);
                if (StringUtils.isEmpty(response)) {
                    // 重新注册监听器
                    SageCodeXManage.checkAndAddDocumentListener(editor);
                    return;
                }
                int tabWidth = editor.getSettings().getTabSize(editor.getProject());
                List<String> lines = RenderUtil.replaceLeadingTabs(response, tabWidth);
                StringBuilder emptyData = new StringBuilder();
                for (int i = 0; i < lines.size(); i++) {
                    emptyData.append(System.lineSeparator());
                }
                // 插入空行，将编辑区中内联元素的下面的代码向下移动
                editor.getDocument().insertString(lineStartOffset, emptyData.toString());

                editor.getInlayModel().addInlineElement(lineStartOffset, true, new SageCodeXCustomElementRenderer(response, lines, editor));
                System.out.println("--开始注册 光标 - tab 监听器");
                // 添加"鼠标"监听器
                editor.getCaretModel().addCaretListener(new SageCodeXCaretListener());
                // 添加"tab"监听器
                EditorActionManager.getInstance().setActionHandler("EditorTab", new SageCodeXEditorTabListener());
                System.out.println("-- 光标 - tab 注册监听器完毕");
            });
        });


    }


    private DocumentContext generateDocumentContext(Editor editor) {
        DocumentContext context = new DocumentContext();
        // 选中的代码
        String selectedText = editor.getSelectionModel().getSelectedText();
        context.setCaretBeforeText(selectedText);
        context.setCodePurposeEnum(CodePurposeEnum.COMMON_GENERATE);

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
