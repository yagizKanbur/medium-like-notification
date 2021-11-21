package com.trendyol.notificationdispatcher.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.notificationdispatcher.consumer.message.FollowersListMessage;
import com.trendyol.notificationdispatcher.service.NotificationDispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FollowersListConsumer {
    private final NotificationDispatcherService notificationDispatcherService;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "notification-info",
            groupId = "notification-dispatcher-consumer-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeActiveLimitChangedMessage(@Payload String message,
                                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                                 @Header(KafkaHeaders.OFFSET) int offset) throws JsonProcessingException {
        log.info("[START] consumeArticleCreatedMessage Message: {}, Partition: {}, Offset: {}", message, partition, offset);
        FollowersListMessage messageAsObject = mapper.readValue(message,FollowersListMessage.class);
        notificationDispatcherService.consume(messageAsObject);
        log.info("[END] consumeArticleCreatedMessage Message: {} Partition: {}, Offset: {}", message, partition, offset);
    }
}
