package com.sage.codex.sagecodex.constant;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.ui.HyperlinkAdapter;
import com.intellij.ui.HyperlinkLabel;
import com.sage.codex.sagecodex.action.SageCodeXSettingAction;
import com.sage.codex.sagecodex.config.SageCodeXSettingConfiguration;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

/**
 * @Description： 常量类，用于定义常量参数
 * @Author: xionghao
 * @Date: 2023/11/25 16:48
 */
public class Constant {



    /**
     * 登录交易码
     */
    public static final String LOGIN_TRANSACTION_CODE = "/codeXLogin.do";

    /**
     * chat聊天交易码
     */
    public static final String CHAT_TRANSACTION_CODE = "/v3/api/codex/chat/generation";


    /**
     * prompt查询
     */
    public static final String CODEX_PROMPT_QRY = "/codexPromptQryList.do";


    /**
     * 创建会话
     */
    public static final String CREATE_CHAT = "/createChat.do";


    public static final String INSTRUCT_QUERY = "/qryCodePurposeCommand.do";


    /**
     * 点赞/点踩
     */
    public static final String LIKE_STOMP = "/likeStomp.do";


    // 操作手册链接地址
    public static final String SAGE_CODE_X_DOCUMENT_URL = "https://nq07ifxm2ty.feishu.cn/docx/A4bgdW2IhoMONox0GSica4Jbn2d";




//    public static final String LOGIN_THEME = "file:///Users/4paradigm/idea-project/sagegpt-codex-idea-plugin/src/main/resources/webview/login.html";
//    public static final String INDEX_THEME = "file:///Users/4paradigm/idea-project/sagegpt-codex-idea-plugin/src/main/resources/webview/index.html";
//    public static final String LOGIN_THEME_BLACK = "file:///Users/4paradigm/idea-project/sagegpt-codex-idea-plugin/src/main/resources/webview/login-black.html";
//    public static final String INDEX_THEME_BLACK = "file:///Users/4paradigm/idea-project/sagegpt-codex-idea-plugin/src/main/resources/webview/index-black.html";


    public static final String LOGIN_THEME = "http://172.21.80.15:8801/login.html";
    public static final String INDEX_THEME = "http://172.21.80.15:8801/index.html";
    public static final String LOGIN_THEME_BLACK = "http://172.21.80.15:8801/login-black.html";
    public static final String INDEX_THEME_BLACK = "http://172.21.80.15:8801/index-black.html";


}
