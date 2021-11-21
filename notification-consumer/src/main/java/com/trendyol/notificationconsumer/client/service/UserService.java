package com.trendyol.notificationconsumer.client.service;

import com.trendyol.notificationconsumer.client.UserApiClient;
import com.trendyol.notificationconsumer.client.response.NotificationPreferencesDto;
import com.trendyol.notificationconsumer.client.response.FollowersDto;
import com.trendyol.notificationconsumer.client.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserApiClient userApiClient;

    public FollowersDto getFollowers (String userId){
        UserApiResponse<FollowersDto> userApiResponse;
        try {
            log.info("Calling users/{}/followers", userId);
            userApiResponse = userApiClient.getFollowers(userId);
        } catch (Exception e) {
            log.error("error occurred while getting followers. userId: {} Error: {}", userId, e.getMessage());
            throw new RuntimeException();
        }
        log.info("UserApi users/{userId}/followers{}, Response: {}", userId, userApiResponse);
        return userApiResponse.getData();
    }

    public NotificationPreferencesDto getFollowersNotificationPreference (String userId){
        UserApiResponse<NotificationPreferencesDto> userApiResponse;
        try {
            log.info("Calling users/{}/preferences", userId);
            userApiResponse = userApiClient.getFollowersPreferences(userId);
        } catch (Exception e) {
            log.error("error occurred while getting followers preferences. userId: {} Error: {}", userId, e.getMessage());
            throw new RuntimeException();
        }

        log.info("Called users/{}/preferences, Response: {}", userId, userApiResponse);
        return userApiResponse.getData();
    }
}
