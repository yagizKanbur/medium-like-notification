package com.trendyol.notificationdispatcher.client;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferencesDto;
import com.trendyol.notificationdispatcher.client.response.UserApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "user-service:8080")
public interface UserApiClient {

    @GetMapping(path = "users/{userId}/preferences")
    UserApiResponse<NotificationPreferencesDto> getFollowersPreferences(@PathVariable String userId);
}
