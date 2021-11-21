package com.trendyol.articleservice.exception;

import com.trendyol.articleservice.model.dto.BaseDto;
import com.trendyol.articleservice.model.response.ArticleApiResponse;
import com.trendyol.articleservice.model.response.ErrorDetail;
import com.trendyol.articleservice.model.response.ErrorResponse;
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

    @ExceptionHandler(ArticleApiException.class)
    public ResponseEntity<ArticleApiResponse<BaseDto>> handleArticleException(ArticleApiException ex) {
        log.error("Article-api-exception", ex);
        return prepareErrorResponse(ex.getClass().getSimpleName(), ex.getErrorCode(), ex.getStatusCode(), "{0}: {1}",ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ArticleApiResponse<BaseDto>> handleException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return prepareErrorResponse(ex.getClass().getSimpleName(), ErrorCode.UNEXPECTED, HttpStatus.INTERNAL_SERVER_ERROR, "{0}: {1}", ex.getClass().getSimpleName(), ex.getMessage());
    }

    private ResponseEntity<ArticleApiResponse<BaseDto>> prepareErrorResponse(ErrorResponse errorResponse, HttpStatus statusCode) {
        ArticleApiResponse<BaseDto> response = new ArticleApiResponse<>();
        response.setError(errorResponse);
        response.getError().setStatus(statusCode.value());
        log.error("GlobalExceptionHandler errorResponse: {}, statusCode: {}", errorResponse, statusCode);
        return new ResponseEntity<>(response, statusCode);
    }

    private ResponseEntity<ArticleApiResponse<BaseDto>> prepareErrorResponse(String type, ErrorCode errorCode, HttpStatus statusCode, String message, Object... parameters) {
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
