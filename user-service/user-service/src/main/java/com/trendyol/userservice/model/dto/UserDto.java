package com.trendyol.userservice.model.dto;

import com.trendyol.userservice.model.entity.NotificationPreferences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {
    private String userId;
    private String email;
    private String username;
    private NotificationPreferences notificationPreferences;
    private List<String> followersIds;
    private List<String> subscribedIds;
}
