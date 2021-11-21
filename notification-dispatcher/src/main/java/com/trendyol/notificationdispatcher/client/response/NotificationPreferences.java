package com.trendyol.notificationdispatcher.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum NotificationPreferences {
    UNDEFINED("undefined"),
    MOBILE("mobile"),
    MAIL("mail"),
    ALL("all");

    private static final NotificationPreferences[] values = values();
    private String value;

    NotificationPreferences(String value) {
        this.value = value;
    }

    @JsonCreator
    public static NotificationPreferences parseType(String value) {
        return Arrays.stream(values)
                .filter(item -> item.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(UNDEFINED);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
