package com.trendyol.userservice.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ErrorCode {
    UNEXPECTED(500),
    USER_NOT_SAVED(401),
    USER_NOT_DELETED(402),
    USER_NOT_FOUND(403),
    EMAIL_NOT_VALID(417),
    USER_ALREADY_FOLLOWED(418);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    @JsonCreator
    public static ErrorCode getErrorCode(int code) {
        return Arrays.stream(ErrorCode.values()).filter(e -> e.getCode() == code).findFirst().orElse(ErrorCode.UNEXPECTED);
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
