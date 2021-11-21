package com.trendyol.notificationconsumer.client;

import com.trendyol.notificationconsumer.client.response.FollowersDto;
import com.trendyol.notificationconsumer.client.response.NotificationPreferencesDto;
import com.trendyol.notificationconsumer.client.response.UserApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "user-service:8080")
public interface UserApiClient {
    @GetMapping(path = "users/{userId}/followers")
    UserApiResponse<FollowersDto> getFollowers(@PathVariable String userId);

    @GetMapping(path = "users/{userId}/preferences")
    UserApiResponse<NotificationPreferencesDto> getFollowersPreferences(@PathVariable String userId);
}
