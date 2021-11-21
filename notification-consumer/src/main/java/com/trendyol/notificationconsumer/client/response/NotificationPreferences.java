package com.trendyol.notificationconsumer.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

public enum NotificationPreferences {
    UNDEFINED("undefined"),
    MOBILE("mobile"),
    MAIL("mail"),
    BOTH("both");

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
