package com.trendyol.notificationdispatcher.service;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferencesDto;
import com.trendyol.notificationdispatcher.client.service.UserService;
import com.trendyol.notificationdispatcher.consumer.message.FollowersListMessage;
import com.trendyol.notificationdispatcher.producer.KafkaProducer;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationDispatcherService {
    private final UserService userApiService;
    private final NotificationPreferenceServiceFactory preferencesServiceFactory;

    public void consume(FollowersListMessage message) {
        List<String> followersList = message.getFollowersList();

        NotificationMessage notificationMessage = NotificationMessage.builder()
                .authorId(message.getAuthorId())
                .title(message.getTitle())
                .url(message.getUrl())
                .build();

        for (String s : followersList) {
            NotificationPreferencesDto prefResponse = userApiService.getFollowersNotificationPreference(s);
            notificationMessage.setEmail(prefResponse.getEmail());
            notificationMessage.setReceiverId(s);
            preferencesServiceFactory.process(prefResponse.getPreferences(),notificationMessage);
        }

    }


}
