//package com.sage.codex.sagecodex.config;
//
//import com.intellij.openapi.project.Project;
//import com.sage.codex.sagecodex.constant.Constant;
//import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
//import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
//import org.apache.commons.lang3.StringUtils;
//
///**
// * @Description： 配置类
// * @Author: xionghao
// * @Date: 2023/11/21 10:10
// */
//public class SageCodeXProperties {
//
//    /**
//     * 服务调用地址:  http://172.16.16.76:18810
//     */
//    public static String URL = SageConfigurationSetting.settings().serverUrl;
//
//    /**
//     * 用户名称，如：4pdadmin
//     */
//    public static String TELLER_ID = UserInfoConfigurationSetting.settings().tellerId;
//
//
//    public static String TELLER_NAME = UserInfoConfigurationSetting.settings().tellerName;
//
//
//    /**
//     * x-token信息 ，如： eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJUT0tFTl9TRUNSRVQiOiI1eGNKVnJYTnlRREl4SzFsMlJTOW53IiwiZXhwIjoxNzAxMDk1NTMwLCJ1c2VyIjoie1wiZW1haWxcIjpcIlwiLFwiZW50SWRcIjpcIjBcIixcImxvZ2luXCI6dHJ1ZSxcImxvZ291dFwiOmZhbHNlLFwibmFtZVwiOlwiNHBkYWRtaW5cIixcInBhc3N3b3JkXCI6XCIyYTdkZjlkNDA1OGI5YTM5NTFlZWJjMzlmYmRhMTZmOFwiLFwicGhvbmVcIjpcIjEzNDg3NzY1MjgxXCIsXCJzdGF0dXNcIjpcIjBcIixcInRlbGxlclNlcVwiOjAsXCJ0ZWxsZXJTdGF0ZVwiOlwiMFwiLFwidGVsbGVyVHlwZVwiOlwiMVwiLFwidW5pcXVlSWRcIjpcIjRwZGFkbWluXCIsXCJ1c2VySWRcIjpcIjRwZGFkbWluXCJ9IiwiaWF0IjoxNzAxMDUyMzMwLCJ1bmlxdWVJZCI6IjRwZGFkbWluIn0.EIxknlzsLvaGzJfNA51259qZK56rGRqTjgXwxzNxJ2Q
//     */
//    public static String X_TOKEN = UserInfoConfigurationSetting.settings().xToken;
//
//    public static String CHAT_ID = UserInfoConfigurationSetting.settings().chatId;
//
//
//    public static boolean checkServerUrlIsEmpty(Project project) {
////        String serverUrl = settings().serverUrl;
//        String serverUrl = SageCodeXProperties.URL;
//        if (serverUrl == null || StringUtils.isEmpty(serverUrl)) {
//            Constant.notifications(project, "请先设置服务url地址");
//            return true;
//        }
//        return false;
//    }
//}
