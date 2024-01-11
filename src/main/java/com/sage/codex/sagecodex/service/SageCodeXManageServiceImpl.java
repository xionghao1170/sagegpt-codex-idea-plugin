package com.sage.codex.sagecodex.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.sage.codex.sagecodex.constant.Constant;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import com.sage.codex.sagecodex.enums.CodeEnvEnum;
import com.sage.codex.sagecodex.enums.CodePurposeEnum;
import com.sage.codex.sagecodex.enums.RequestStatauEnum;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.model.*;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationState;
import com.sage.codex.sagecodex.theme.SageCodeXManage;
import com.sage.codex.sagecodex.theme.SageCodeXStatusService;
import com.sage.codex.sagecodex.utils.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefBrowser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/11/20 20:33
 */

public class SageCodeXManageServiceImpl implements SageCodeXManageService {


    /**
     * @param project  项目对象
     * @param req      入参对象
     * @param onlyCode 是否只需要代码部分
     * @return
     */
    @Override
    public String doHttpPost(Project project, GenerationReq req, boolean onlyCode) {
        String serverUrl = SageConfigurationSetting.settings().serverUrl;
        String response = HttpClientUtil.doPostJson(project, serverUrl + Constant.CHAT_TRANSACTION_CODE, JSON.toJSONString(req));
        checkResponse(project, response);
        System.out.println("response: " + response);
        // 校验返回结果
        if (Objects.nonNull(response)) {
            // 返回的格式转换
            return HttpClientUtil.convertDataFormat(response, onlyCode);
        }
        return null;
    }

    private boolean checkResponse(Project project, String response) {
        if (StringUtils.isEmpty(response)) {
            return false;
        }
        if ("data:[DONE]".equals(response) || response == null) {
            return false;
        }
        if (response.startsWith("data:")) {
            response = response.substring(5);
        }
        CommonResponse resp = JSON.parseObject(response, CommonResponse.class);
        if (RequestStatauEnum.SUCCESS.getCode().equals(resp.getCode())) {
            return true;
        } else {
            if (RequestStatauEnum.SESSION_FAIL.getCode().equals(resp.getCode())) {
                // 发送消息提示
                SageCodeXNotifications.notifications(project, resp.getMessage());
                // 重新跳转到登录页并显示窗口
                SageCodeXManage.initLoginPage(project);
                // 发送通知，更新barWidget
                SageCodeXStatusService.notifyApplication(SageCodeXStatus.NotSignedIn);
            }
        }

        return false;
    }

    @Override
    public String doHttpPost(Project project, String transactionCode, Map<String, Object> params) {
        String serverUrl = SageConfigurationSetting.settings().serverUrl;
        return HttpClientUtil.doPostJson(project, serverUrl + transactionCode, JSON.toJSONString(params));
    }


    @Override
    public boolean login(Project project, String account, String password) {
        String serverUrl = SageConfigurationSetting.settings().serverUrl;
        String loginUrl = serverUrl + Constant.LOGIN_TRANSACTION_CODE;
        UserLoginReq userLoginReq = new UserLoginReq(account, password);
        String response = HttpClientUtil.doPostJson(project, loginUrl, JSON.toJSONString(userLoginReq));
        CommonResponse resp = JSON.parseObject(response, CommonResponse.class);
        SageCodeXStatus status;
        boolean loginResult;
        if (RequestStatauEnum.SUCCESS.getCode().equals(resp.getCode())) {
            String xToken = (String) resp.getData().get("x-token");
            String tellerId = (String) resp.getData().get("tellerId");
            String tellerName = (String) resp.getData().get("tellerName");

            status = SageCodeXStatus.Ready;
            UserInfoConfigurationSetting.settings().tellerId = tellerId;
            UserInfoConfigurationSetting.settings().tellerName = tellerName;
            UserInfoConfigurationSetting.settings().xToken = xToken;
            UserInfoConfigurationSetting.settings().status = status;
            UserInfoConfigurationSetting userInfoConfigurationSetting = ApplicationManager.getApplication().getService(UserInfoConfigurationSetting.class);
            UserInfoConfigurationState currentState = userInfoConfigurationSetting.getState();
            // 修改 currentState 对象的属性
            userInfoConfigurationSetting.loadState(currentState);

            loginResult = true;
        } else {
            SageCodeXNotifications.notifications(project, resp.getMessage());
            // 发送通知，更新barWidget
            status = SageCodeXStatus.NotSignedIn;
            loginResult = false;
        }
        // 发送通知，更新barWidget
        SageCodeXStatusService.notifyApplication(status);
        return loginResult;
    }


    @Override
    public List<String> loadPrompt(Project project, CefBrowser browser) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("promptType", "user");
        String response = doHttpPost(project, Constant.CODEX_PROMPT_QRY, params);
        if (StringUtils.isEmpty(response)) {
            return Collections.emptyList();
        }
        BaseResponse baseResponse = JSON.parseObject(response, BaseResponse.class);
        if (!RequestStatauEnum.SUCCESS.getCode().equals(baseResponse.getCode())) {
            SageCodeXNotifications.notifications(project, baseResponse.getMessage());
            checkAndRedirectLogin(project, baseResponse.getCode(), browser);
            return Collections.emptyList();
        }
        List<PromptVO> resp = JSON.parseArray(baseResponse.getData().toString(), PromptVO.class);
        System.out.println("resp: " + JSON.toJSONString(resp));
        if (CollectionUtils.isEmpty(resp)) {
            return Collections.emptyList();
        }
        return resp.stream().map(PromptVO::getPromptDescripe).collect(Collectors.toList());

    }

    private void checkAndRedirectLogin(Project project, String code, CefBrowser browser) {
        if (RequestStatauEnum.SESSION_FAIL.getCode().equals(code)) {
            String url = browser.getURL();
            if (Constant.INDEX_THEME.equals(url)) {
                browser.loadURL(Constant.LOGIN_THEME);
            } else {
                browser.loadURL(Constant.LOGIN_THEME_BLACK);
            }
            // 重新跳转到登录页
            SageCodeXManage.initLoginPage(project);
            SageCodeXStatusService.notifyApplication(SageCodeXStatus.NotSignedIn);
        }
    }

    @Override
    public String createSession(Project project, CefBrowser browser) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("codeEnv", "chat");
        String response = doHttpPost(project, Constant.CREATE_CHAT, params);
        BaseResponse baseResponse = JSON.parseObject(response, BaseResponse.class);
        if (!RequestStatauEnum.SUCCESS.getCode().equals(baseResponse.getCode())) {
            SageCodeXNotifications.notifications(project, baseResponse.getMessage());
            checkAndRedirectLogin(project, baseResponse.getCode(), browser);
            return "";
        }
        String chatId = JSON.parseObject(baseResponse.getData().toString()).get("chatId").toString();
        // 会话id保存到缓存中
        UserInfoConfigurationSetting.settings().chatId = chatId;
        return chatId;
    }

    @Override
    public GenerationReq buildGenerationReq(String chatId, String dialogueId, String content, CodeEnvEnum codeEnvEnum, CodePurposeEnum codePurposeEnum, Boolean isStream) {
        // html界面聊天时，language设置为""
        return buildGenerationReq(chatId, dialogueId, "", content, codeEnvEnum, codePurposeEnum, isStream);
    }

    @Override
    public GenerationReq buildGenerationReq(String chatId, String dialogueId, String content, Boolean isStream) {
        return buildGenerationReq(chatId, dialogueId, "", content, CodeEnvEnum.CHAT, CodePurposeEnum.COMMON_CHIT, isStream);
    }


    @Override
    public GenerationReq buildGenerationReq(String chatId, String dialogueId, DocumentContext documentContext, PageContext pageContext, CodeEnvEnum codeEnv) {
        GenerationReq req = buildGenerationReq(chatId, dialogueId, documentContext.getFileTypeName(), documentContext.getCaretBeforeText(), codeEnv, documentContext.getCodePurposeEnum(), false);
        req.setPage_context(pageContext);
        return req;
    }

    @Override
    public GenerationReq buildGenerationReq(String chatId, String dialogueId, String language, String content, CodeEnvEnum codeEnv, CodePurposeEnum codePurpose, Boolean isStream) {
        GenerationReq req = new GenerationReq();
        req.setChat_id(chatId);
        req.setDialogue_id(dialogueId);
        req.setP_dialogue_id("");
        req.setAction("next");

        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent(content);
        message.setRole("user");
        messages.add(message);
        req.setMessages(messages);

        req.setLanguage(StringUtils.isBlank(language) ? "" : language.toLowerCase());
        req.setCode_env(codeEnv.getCode());
        req.setCode_purpose(codePurpose.getCode());
        req.setModel("azure-gpt-35-turbo");
        req.setPrompt_id("");
        req.setStream(isStream);
        req.setRepository_id(Collections.emptyList());
        return req;
    }

    @Override
    public String getInstructList(Project project, CodeEnvEnum codeEnvEnum, String query, CefBrowser browser) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("codeEnv", codeEnvEnum.getCode());
        String response = doHttpPost(project, Constant.INSTRUCT_QUERY, params);
        BaseResponse baseResponse = JSON.parseObject(response, BaseResponse.class);
        if (!RequestStatauEnum.SUCCESS.getCode().equals(baseResponse.getCode())) {
            SageCodeXNotifications.notifications(project, baseResponse.getMessage());
            checkAndRedirectLogin(project, baseResponse.getCode(), browser);
            return null;
        }
        String list = JSON.parseObject(baseResponse.getData().toString()).get("list").toString();
        if (StringUtils.isNotEmpty(list)) {
            return instructionMatching(list, query);
        }
        return null;
    }

    @Override
    public void doLikeStomp(Project project, String dialogueId, String likeType, CefBrowser browser) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("dialogueId", dialogueId);
        params.put("likeType", likeType);
        String response = doHttpPost(project, Constant.LIKE_STOMP, params);
        BaseResponse baseResponse = JSON.parseObject(response, BaseResponse.class);
        if (!RequestStatauEnum.SUCCESS.getCode().equals(baseResponse.getCode())) {
            SageCodeXNotifications.notifications(project, baseResponse.getMessage());
            checkAndRedirectLogin(project, baseResponse.getCode(), browser);
        }

    }


    private String instructionMatching(String data, String query) {
        List<CodePurposeVO> codePurposeVOS = JSON.parseArray(data, CodePurposeVO.class);
        if (CollectionUtils.isEmpty(codePurposeVOS)) {
            return null;
        }
        List<CodePurposeVO> collect = codePurposeVOS.stream().map(e -> {
            e.setAlias("/" + e.getAlias());
            return e;
        }).collect(Collectors.toList());
        collect = collect.stream().filter(e -> e.getAlias().startsWith(query)).collect(Collectors.toList());
        return JSON.toJSONString(collect);
    }


}
