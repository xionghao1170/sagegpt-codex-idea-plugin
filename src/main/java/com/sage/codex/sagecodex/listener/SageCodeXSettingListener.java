package com.sage.codex.sagecodex.listener;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @Description： sageCodeX 配置页面监听器
 * @Author: xionghao
 * @Date: 2023/11/22 10:36
 */
public class SageCodeXSettingListener implements FocusListener {


    private final String defaultHint;
    private final JTextField textField;

    public SageCodeXSettingListener(JTextField textField, String defaultHint) {
        this.defaultHint = defaultHint;
        this.textField = textField;
    }

    // 获得焦点
    @Override
    public void focusGained(FocusEvent e) {
        // 清空提示语，设置为黑色字体
        if (textField.getText().equals(defaultHint)) {
            textField.setText("");
            textField.setForeground(JBColor.BLACK);
        }
    }

    // 失去焦点
    @Override
    public void focusLost(FocusEvent e) {
        // 如果内容为空，设置提示语
        if (textField.getText().equals("")) {
            textField.setText(defaultHint);
            textField.setForeground(JBColor.GRAY);
        }
    }
}
