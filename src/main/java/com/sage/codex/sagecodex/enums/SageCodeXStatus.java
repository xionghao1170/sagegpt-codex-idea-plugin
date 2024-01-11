package com.sage.codex.sagecodex.enums;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.ui.PresentableEnum;
import com.sage.codex.sagecodex.constant.SageCodeXBundle;
import com.sage.codex.sagecodex.constant.SageCodeXIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum SageCodeXStatus implements PresentableEnum {
    Ready,
    NotSignedIn,
    CompletionInProgress,
    AgentWarning,
    AgentError,
    AgentBroken,
    IncompatibleClient,
    Unsupported,
    UnknownError,
    Inactive;

    public boolean isDisablingClientRequests() {
        return this == IncompatibleClient || this == AgentBroken;
    }


    @NotNull
    public Icon getIcon() {
        switch (this) {
            case Ready:
            case NotSignedIn:
                return SageCodeXIcons.SageCodeX;
            case CompletionInProgress:
                return SageCodeXIcons.StatusBarCompletionInProgress;
            case AgentWarning:
                return SageCodeXIcons.CopilotWarning;
            case AgentError:
            case AgentBroken:
            case IncompatibleClient:
            case Unsupported:
            case UnknownError:
                return SageCodeXIcons.CopilotNotAvailable;
            default:
                return SageCodeXIcons.CopilotDisconnected;
        }
    }

    @Override
    public @NlsContexts.Label String getPresentableText() {
        switch (this) {
            case Ready:
                return SageCodeXBundle.get("sageCodeXStatus.ready");
            case CompletionInProgress:
                return SageCodeXBundle.get("sageCodeXStatus.completionInProgress");
            case AgentWarning:
                return SageCodeXBundle.get("sageCodeXStatus.agentWarning");
            case AgentError:
                return SageCodeXBundle.get("sageCodeXStatus.agentError");
            case AgentBroken:
                return SageCodeXBundle.get("sageCodeXStatus.agentBroken");
            case IncompatibleClient:
                return SageCodeXBundle.get("sageCodeXStatus.incompatibleClient");
            case Unsupported:
                return SageCodeXBundle.get("sageCodeXStatus.unsupported");
            case UnknownError:
                return SageCodeXBundle.get("sageCodeXStatus.unknownError");
            case NotSignedIn:
                return SageCodeXBundle.get("sageCodeXStatus.notSignedIn");
            case Inactive:
                return SageCodeXBundle.get("sageCodeXStatus.agentInactive");
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
