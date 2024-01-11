package com.sage.codex.sagecodex.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.JBColor;
import com.sage.codex.sagecodex.listener.SageCodeXSettingListener;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import com.sage.codex.sagecodex.setting.SageConfigurationState;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @Description： sageCodeX 配置页面
 * @Author: xionghao
 * @Date: 2023/11/22 10:26
 */
public class SageCodeXSettingConfiguration implements Configurable {

    private boolean isModified = false;

    private final JComponent component;
    private final JTextField url;
    private final static String appHost = "请输入服务地址，如:http://172.16.16.76:18810";

    // 构造器，IDE 在初始化我们插件的时候，会实例化拓展点对象，而实例化时只能通过无参构造器创建对象。
    public SageCodeXSettingConfiguration() {
        this.component = new JPanel();
        this.component.setLayout(new VerticalLayout());

        JLabel title = new JLabel("服务地址：");
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        component.add(title);
        // 创建url文本框
        this.url = new JTextField();
        url.setPreferredSize(new Dimension(0, 50));
        // 持久化的数据是否已经存在
        if (SageConfigurationSetting.settings().serverUrl != null) {//存在
            this.url.setText(SageConfigurationSetting.settings().serverUrl);
        } else {// 不存在
            //设置输入框提示语
            this.url.setText(appHost);
            this.url.setForeground(JBColor.GRAY);
        }
        this.url.addFocusListener(new SageCodeXSettingListener(this.url, appHost));
        this.component.add(this.url);
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "SageCodeXConfiguration";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return component;
    }

    @Override
    public boolean isModified() {
        if (SageConfigurationSetting.settings().serverUrl != null) {
            isModified = !SageConfigurationSetting.settings().serverUrl.equals(url.getText());
        } else {
            isModified = !appHost.equals(url.getText());
        }
        return isModified;
    }

    @Override
    public void reset() {
        // 重置配置
        isModified = false;
    }

    // 当在配置页面点击 apply 或者 ok 按钮时，该方法会被调用
    @Override
    public void apply() throws ConfigurationException {
        System.out.println("url地址为：" + url.getText());
        // 点击 apply 的时候 会被调用
        SageConfigurationSetting.settings().serverUrl = url.getText();
        SageConfigurationSetting sageConfigurationSetting = ApplicationManager.getApplication().getService(SageConfigurationSetting.class);
        SageConfigurationState currentState = sageConfigurationSetting.getState();
        // 修改 currentState 对象的属性
        sageConfigurationSetting.loadState(currentState);
        System.out.println("----serverUrl:" + SageConfigurationSetting.settings().serverUrl);
    }
}
