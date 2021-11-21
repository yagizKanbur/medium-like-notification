package com.trendyol.notificationdispatcher.service;

import com.trendyol.notificationdispatcher.producer.KafkaProducer;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSendService {
    private final KafkaProducer kafkaProducer;

    public void sendToEmailNotificationTopic(NotificationMessage message) {
        log.info("Message send to email-notification topic. message: {}", message);
        kafkaProducer.sendMessage(message.getAuthorId(),message,"email-notification");
    }


    public void sendToMobileNotificationTopic(NotificationMessage message) {
        log.info("Message send to mobile-notification topic. message: {}", message);
        kafkaProducer.sendMessage(message.getAuthorId(),message,"mobile-notification");
    }
}
