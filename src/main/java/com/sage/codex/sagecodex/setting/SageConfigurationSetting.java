package com.sage.codex.sagecodex.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.sage.codex.sagecodex.constant.Constant;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @Description： 插件url地址配置持久化
 * @Author: xionghao
 * @Date: 2023/11/22 11:23
 */
@State(name = "sageCodexConfiguration", storages = {@Storage(value = "sageCodexConfiguration.xml")})
public class SageConfigurationSetting implements PersistentStateComponent<SageConfigurationState> {
    public SageConfigurationState state;

    @NotNull
    public static SageConfigurationState settings() {
        return Objects.requireNonNull((ApplicationManager.getApplication().getService(SageConfigurationSetting.class)).getState());
    }

    @Override
    public @Nullable SageConfigurationState getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull SageConfigurationState state) {
        this.state = state;
    }


    @Override
    public synchronized void noStateLoaded() {
        this.state = new SageConfigurationState();
    }

    public static boolean checkServerUrlIsEmpty(Project project) {
        String serverUrl = settings().serverUrl;
        if (StringUtils.isEmpty(serverUrl)) {
            SageCodeXNotifications.serverEmptyNotifications(project);
            return true;
        }
        return false;
    }

}
