//package com.sage.codex.sagecodex.listener;
//
//import com.intellij.openapi.editor.EditorFactory;
//import com.intellij.openapi.project.ModuleListener;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.project.ProjectManager;
//import com.intellij.openapi.project.ProjectManagerListener;
//import com.intellij.openapi.startup.ProjectActivity;
//import kotlin.Unit;
//import kotlin.coroutines.Continuation;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
///**
// * @Description： 项目启动监听器，项目启动时，可以在这里注册监听器
// * @Author: xionghao
// * @Date: 2023/11/22 14:49
// */
//public class SageCodeXProjectManagerListener implements ProjectActivity {
//    @Nullable
//    @Override
//    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
//        // 注册事件监听器
//        System.out.println("注册事件监听器");
//        EditorFactory.getInstance().addEditorFactoryListener(new SageCodeXEditorFactoryListener(), project);
//        return null;
//    }
//
//
//}
