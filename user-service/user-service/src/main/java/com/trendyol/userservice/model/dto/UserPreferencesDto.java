package com.trendyol.userservice.model.dto;

import com.trendyol.userservice.model.entity.NotificationPreferences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesDto extends BaseDto {
    private NotificationPreferences preferences;
    private String email;
}
