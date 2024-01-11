package com.sage.codex.sagecodex.constant;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 17:58
 */
public final class SageCodeXBundle extends DynamicBundle {
    public static final SageCodeXBundle INSTANCE = new SageCodeXBundle();


    private SageCodeXBundle() {
        super("sagecodex.sagecodex");
    }

    public static String get(@NotNull @PropertyKey(resourceBundle = "sagecodex.sagecodex") String key) {
        return INSTANCE.getMessage(key, new Object[0]);
    }

    public static String get(@NotNull @PropertyKey(resourceBundle = "sagecodex.sagecodex") String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
