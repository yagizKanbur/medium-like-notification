package com.trendyol.userservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class UserApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus statusCode;

    public UserApiException(ErrorCode errorCode, HttpStatus statusCode, String message, Object... parameters) {
        super(MessageFormat.format(message, parameters));
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        log.error(MessageFormat.format(message, parameters));
    }

    public UserApiException(ErrorCode errorCode, HttpStatus statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        log.error(message);
    }

    public UserApiException(String message) {
        this(message, ErrorCode.UNEXPECTED, HttpStatus.BAD_REQUEST);
    }

    public UserApiException(String message, Object... parameters) {
        this(ErrorCode.UNEXPECTED, HttpStatus.BAD_REQUEST, message, parameters);
    }
}
