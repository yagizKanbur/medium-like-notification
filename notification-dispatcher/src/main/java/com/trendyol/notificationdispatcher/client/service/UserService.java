package com.trendyol.notificationdispatcher.client.service;

import com.trendyol.notificationdispatcher.client.UserApiClient;
import com.trendyol.notificationdispatcher.client.response.NotificationPreferencesDto;
import com.trendyol.notificationdispatcher.client.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserApiClient userApiClient;

    public NotificationPreferencesDto getFollowersNotificationPreference (String userId){
        UserApiResponse<NotificationPreferencesDto> userApiResponse;
        try {
            log.info("Calling users/{}/preferences", userId);
            userApiResponse = userApiClient.getFollowersPreferences(userId);
        } catch (Exception e) {
            log.error("error occurred while Calling users/get-user-preferences/{} Error: {}", userId, e.getMessage());
            throw new RuntimeException();
        }

        log.info("Called users/{}/preferences, Response: {}", userId, userApiResponse);
        return userApiResponse.getData();
    }
}
