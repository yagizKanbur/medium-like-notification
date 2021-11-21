package com.trendyol.notificationdispatcher.service;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferences;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;

public interface NotificationPreferenceService {
    boolean accept(NotificationPreferences notificationPreferences);

    void execute(NotificationPreferences notificationPreferences, NotificationMessage message);
}
