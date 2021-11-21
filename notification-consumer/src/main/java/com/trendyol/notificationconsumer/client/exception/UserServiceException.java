package com.trendyol.notificationconsumer.client.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
        log.error(message);
    }
}
