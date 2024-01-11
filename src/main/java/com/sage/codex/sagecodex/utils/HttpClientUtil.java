package com.sage.codex.sagecodex.utils;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.project.Project;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.model.Choice;
import com.sage.codex.sagecodex.model.*;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.theme.SageCodeXStatusService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description： http请求工具类
 * @Author: xionghao
 * @Date: 2023/11/21 10:18
 */
public class HttpClientUtil {


    // 定义正则表达式匹配 Markdown 代码块
    private static final Pattern PATTERN_CONTAIN_CHAR = Pattern.compile("```([a-zA-Z]+)?\\n(.+?)\\n```", Pattern.DOTALL);
    private static final Pattern PATTERN_ONLY_CODE = Pattern.compile("```([\\s\\S]*?)```");

    /**
     * 传送json类型的post请求
     *
     * @param url
     * @param jsonData
     * @return String
     */
    public static String doPostJson(Project project, String url, String jsonData) {
        System.out.println("jsonData:" + jsonData);
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置请求超时时间
            httpPost.setConfig(RequestConfig.custom().setConnectTimeout(8000).build());
            // header中设置token
            httpPost.setHeader("x-token", StringUtils.isEmpty(UserInfoConfigurationSetting.settings().xToken) ? "" : UserInfoConfigurationSetting.settings().xToken);
            // 创建请求内容
            StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            response.getStatusLine().getStatusCode();
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            SageCodeXNotifications.notifications(project, e.getMessage());
            SageCodeXStatusService.notifyApplication(SageCodeXStatus.Ready);
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                SageCodeXStatusService.notifyApplication(SageCodeXStatus.Ready);
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static int getContentPanelChildrenHeight(JPanel contentPanel) {
        int allChildrenHeight = 0;
        Component[] components = contentPanel.getComponents();
        for (Component component : components) {
            Dimension preferredSize = component.getPreferredSize();
            allChildrenHeight += preferredSize.height;
        }
        return allChildrenHeight;
    }


    /**
     * 将返回的数据转换为指定格式
     *
     * @param response 接口返回的数据
     * @param onlyCode 是否只需要代码块，true：只需要代码块，false：需要全部数据
     * @return
     */
    public static String convertDataFormat(String response, boolean onlyCode) {
        if ("data:[DONE]".equals(response) || response == null) {
            return "";
        }
        if (response.startsWith("data:")) {
            response = response.substring(5);
        }
        GenerationResp resp = JSONObject.parseObject(response, GenerationResp.class);
        List<Choice> choices = resp.getChoices();
        if (CollectionUtils.isEmpty(choices)) {
            return "";
        }
        Choice choice = choices.get(0);
        Message message = choice.getMessage();
        String content = null;
        if (Objects.isNull(message)) {
            Delta delta = choice.getDelta();
            if (Objects.isNull(delta)) {
                return null;
            }
            content = delta.getContent();
        } else {
            content = message.getContent();
        }
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        return transformCode(content, onlyCode);
    }


    private static String transformCode(String content, boolean onlyCode) {
        // 匹配 Markdown 代码块
        Matcher matcher = PATTERN_CONTAIN_CHAR.matcher(content);
        // 切分 Markdown 文本并保留代码块
        List<CodeBlockVO> splitTextList = Lists.newArrayList();
        int start = 0;
        String language = "java";
        String firstCodeBlock = null;
        while (matcher.find()) {
            String block = content.substring(start, matcher.start());
            splitTextList.add(new CodeBlockVO(block.replace("\n", "<br/>"), false));
            String markdownCodeBlock = matcher.group(0);
            if (Objects.isNull(firstCodeBlock)) {
                firstCodeBlock = markdownCodeBlock;
            }
            splitTextList.add(new CodeBlockVO(markdownCodeBlock, true));
            start = matcher.end();
            language = !StringUtils.isEmpty(matcher.group(1))
                    ? matcher.group(1)
                    : language;
            // 代码块内容，去掉```
            if (onlyCode) {
                firstCodeBlock = matcher.group(2);
            }

        }
        splitTextList.add(new CodeBlockVO(content.substring(start).replace("\n", "<br/>"), false));
        if (onlyCode) {
            // 只需要代码块
            if (Objects.isNull(firstCodeBlock)) {
                Matcher reMatcher = PATTERN_ONLY_CODE.matcher(content);
                if (reMatcher.find()) {
                    firstCodeBlock = reMatcher.group(1);
                } else {
                    firstCodeBlock = content;
                }
            }
            return firstCodeBlock;
        } else {
            JSONObject jsonObject = new JSONObject();
            // 输出切分结果
            jsonObject.put("data", splitTextList);
            jsonObject.put("language", language);
            return jsonObject.toJSONString();
        }
    }


}