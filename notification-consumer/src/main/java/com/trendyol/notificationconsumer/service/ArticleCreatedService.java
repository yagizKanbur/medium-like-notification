package com.trendyol.notificationconsumer.service;

import com.trendyol.notificationconsumer.client.response.NotificationPreferencesDto;
import com.trendyol.notificationconsumer.client.response.FollowersDto;
import com.trendyol.notificationconsumer.client.service.UserService;
import com.trendyol.notificationconsumer.consumer.message.ArticleCreatedMessage;
import com.trendyol.notificationconsumer.producer.KafkaProducer;
import com.trendyol.notificationconsumer.producer.NotificationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleCreatedService {
    private final UserService userApiService;
    private final KafkaProducer kafkaProducer;

    public void consume(ArticleCreatedMessage message) {
        FollowersDto response = userApiService.getFollowers(message.getAuthorId());

        NotificationInfo notificationInfo = NotificationInfo.builder()
                .authorId(message.getAuthorId())
                .title(message.getTitle())
                .url(message.getUrl())
                .followersList(response.getFollower())
                .build();

        log.info("Message send: {} to {} topic", notificationInfo, "notification-info");
        kafkaProducer.sendMessage(notificationInfo.getAuthorId(), notificationInfo, "notification-info");

    }
}
