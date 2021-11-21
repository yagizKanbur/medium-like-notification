package com.trendyol.userservice.exception;

import com.trendyol.userservice.model.dto.BaseDto;
import com.trendyol.userservice.model.response.ErrorDetail;
import com.trendyol.userservice.model.response.ErrorResponse;
import com.trendyol.userservice.model.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Collections;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserApiResponse<BaseDto>> handleException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return prepareErrorResponse(ex.getClass().getSimpleName(), ErrorCode.UNEXPECTED, HttpStatus.INTERNAL_SERVER_ERROR, "{0}: {1}", ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<UserApiResponse<BaseDto>> handleUserApiException(UserApiException e) {
        log.error("UserApiException", e);
        return prepareErrorResponse(e.getClass().getSimpleName(), e.getErrorCode(), e.getStatusCode(), "{0}: {1}",e.getClass().getSimpleName(), e.getMessage());
    }

    private ResponseEntity<UserApiResponse<BaseDto>> prepareErrorResponse(ErrorResponse errorResponse, HttpStatus statusCode) {
        UserApiResponse<BaseDto> response = new UserApiResponse<>();
        response.setError(errorResponse);
        response.getError().setStatus(statusCode.value());
        log.error("GlobalExceptionHandler errorResponse: {}, statusCode: {}", errorResponse, statusCode);
        return new ResponseEntity<>(response, statusCode);
    }

    private ResponseEntity<UserApiResponse<BaseDto>> prepareErrorResponse(String type, ErrorCode errorCode, HttpStatus statusCode, String message, Object... parameters) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getCode())
                .errors(Collections.singletonList(ErrorDetail.builder()
                        .code(errorCode.getCode())
                        .title(MessageFormat.format(message, parameters))
                        .type(type)
                        .build()))
                .build();
        return prepareErrorResponse(errorResponse, statusCode);
    }
}
