package com.sage.codex.sagecodex.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory;
import com.sage.codex.sagecodex.constant.SageCodeXBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 15:46
 */
public class SageCodeXStatusBarWidgetFactory extends StatusBarEditorBasedWidgetFactory {

    @Override
    public @NonNls @NotNull String getId() {
        return SageCodeXBundle.get("sageCodeX.barWidget.id");
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return SageCodeXBundle.get("sageCodeX.barWidget.displayName");
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new SageCodeXStatusBarWidget(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
        System.out.println("关闭时调用disposeWidget");
    }

}
