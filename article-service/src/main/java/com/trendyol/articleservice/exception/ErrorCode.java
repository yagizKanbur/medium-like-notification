package com.trendyol.articleservice.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ErrorCode {
    UNEXPECTED(500),
    ARTICLE_NOT_SAVED(401),
    ARTICLE_NOT_DELETED(402),
    ARTICLE_NOT_FOUND(403);

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
