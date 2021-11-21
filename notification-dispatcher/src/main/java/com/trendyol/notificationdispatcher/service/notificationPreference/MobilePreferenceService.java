package com.trendyol.notificationdispatcher.service.notificationPreference;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferences;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;
import com.trendyol.notificationdispatcher.service.NotificationDispatcherService;
import com.trendyol.notificationdispatcher.service.NotificationPreferenceService;
import com.trendyol.notificationdispatcher.service.NotificationSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MobilePreferenceService implements NotificationPreferenceService {
    private final NotificationSendService notificationSendService;

    @Override
    public boolean accept(NotificationPreferences notificationPreferences) {
        return (NotificationPreferences.MOBILE.getValue().equals(notificationPreferences.getValue())
                || NotificationPreferences.ALL.getValue().equals(notificationPreferences.getValue()));
    }

    @Override
    public void execute(NotificationPreferences notificationPreferences, NotificationMessage message) {
        notificationSendService.sendToMobileNotificationTopic(message);
    }
}
