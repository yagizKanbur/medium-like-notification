package com.trendyol.notificationdispatcher.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferencesDto extends BaseDto {
    private NotificationPreferences preferences;
    private String email;
}
