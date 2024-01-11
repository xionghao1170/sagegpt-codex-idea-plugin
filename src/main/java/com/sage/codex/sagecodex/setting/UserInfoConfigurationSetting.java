package com.sage.codex.sagecodex.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Description： 用户登陆信息配置持久化
 * @Author: xionghao
 * @Date: 2023/11/27 17:07
 */
@State(name = "sageCodeXUserConfiguration", storages = {@Storage(value = "sageCodeXUserConfiguration.xml")})
public class UserInfoConfigurationSetting implements PersistentStateComponent<UserInfoConfigurationState> {

    private UserInfoConfigurationState state;


    @NotNull
    public static UserInfoConfigurationState settings() {
        return (ApplicationManager.getApplication().getService(UserInfoConfigurationSetting.class)).getState();
    }


    @Override
    public @Nullable UserInfoConfigurationState getState() {
        return this.state;
    }


    @Override
    public synchronized void loadState(@NotNull UserInfoConfigurationState state) {
        this.state = state;
    }

    @Override
    public synchronized void noStateLoaded() {
        this.state = new UserInfoConfigurationState();
    }
}
