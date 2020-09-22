package com.ryoma0622.niceactionstacker.config;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MentionProcessType {
    STARTUP("startup"),
    SET_REACTION("setReaction"),
    REMOVE_REACTION("removeReaction");

    private final String actionName;

    public static MentionProcessType of(String actionName) {
        return Arrays.stream(values()).filter(v -> v.actionName.equals(actionName)).findFirst().orElse(null);
    }
}
