package com.trendyol.notificationdispatcher.service.notificationPreference;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferences;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;
import com.trendyol.notificationdispatcher.service.NotificationDispatcherService;
import com.trendyol.notificationdispatcher.service.NotificationPreferenceService;
import com.trendyol.notificationdispatcher.service.NotificationSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailPreferenceService implements NotificationPreferenceService {
    private final NotificationSendService notificationSendService;

    @Override
    public boolean accept(NotificationPreferences notificationPreferences) {
        return (NotificationPreferences.MAIL.getValue().equals(notificationPreferences.getValue())
                || NotificationPreferences.ALL.getValue().equals(notificationPreferences.getValue()));
    }

    @Override
    public void execute(NotificationPreferences notificationPreferences, NotificationMessage message) {
        notificationSendService.sendToEmailNotificationTopic(message);
    }
}
