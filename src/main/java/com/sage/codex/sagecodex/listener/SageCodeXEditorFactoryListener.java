package com.sage.codex.sagecodex.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.sage.codex.sagecodex.config.ProjectGlobalConfig;
import com.sage.codex.sagecodex.handler.SageCodeXDocumentListener;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/12 15:53
 */
public class SageCodeXEditorFactoryListener implements EditorFactoryListener {


    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
//        System.out.println("注册documentListener----------");
        SageCodeXDocumentListener documentListener = new SageCodeXDocumentListener(event.getEditor());
        editor.getDocument().addDocumentListener(documentListener); // 添加文档监听器
        ProjectGlobalConfig.setDocumentListener(documentListener);

    }
}
