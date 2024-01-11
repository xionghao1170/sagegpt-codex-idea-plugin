package com.sage.codex.sagecodex.service;

import com.intellij.openapi.project.Project;
import com.sage.codex.sagecodex.enums.CodeEnvEnum;
import com.sage.codex.sagecodex.enums.CodePurposeEnum;
import com.sage.codex.sagecodex.model.DocumentContext;
import com.sage.codex.sagecodex.model.GenerationReq;
import com.sage.codex.sagecodex.model.PageContext;
import org.cef.browser.CefBrowser;

import java.util.List;
import java.util.Map;

public interface SageCodeXManageService {

    String doHttpPost(Project project, GenerationReq req, boolean onlyCode);

    String doHttpPost(Project project, String transactionCode, Map<String, Object> params);

    boolean login(Project project, String account, String password);

    List<String> loadPrompt(Project project, CefBrowser browser);

    String createSession(Project project,CefBrowser browser);


    GenerationReq buildGenerationReq(String chatId, String dialogueId, String content, CodeEnvEnum codeEnvEnum, CodePurposeEnum codePurposeEnum, Boolean isStream);

    GenerationReq buildGenerationReq(String chatId, String dialogueId, String content, Boolean isStream);

    GenerationReq buildGenerationReq(String chatId, String dialogueId, String language, String content, CodeEnvEnum codeEnvEnum, CodePurposeEnum codePurposeEnum, Boolean isStream);

    String getInstructList(Project project,CodeEnvEnum codeEnvEnum,String query,CefBrowser browser);

    void doLikeStomp(Project project, String dialogueId, String likeType,CefBrowser browser);

    GenerationReq buildGenerationReq(String chatId, String dialogueId, DocumentContext documentContext, PageContext pageContext, CodeEnvEnum codeEnvEnum);
}
