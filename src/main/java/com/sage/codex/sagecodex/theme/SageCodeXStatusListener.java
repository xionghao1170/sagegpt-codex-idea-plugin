package com.sage.codex.sagecodex.theme;

import com.intellij.util.messages.Topic;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SageCodeXStatusListener {
    Topic<SageCodeXStatusListener> TOPIC = Topic.create("sagecodex.status", SageCodeXStatusListener.class);

    void onSageCodeXStatus(@NotNull SageCodeXStatus status, @Nullable String var2);
}
