package com.trendyol.notificationconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.notificationconsumer.consumer.message.ArticleCreatedMessage;
import com.trendyol.notificationconsumer.consumer.message.KafkaMessage;
import com.trendyol.notificationconsumer.service.ArticleCreatedService;
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
public class ArticleCreatedConsumer {
    private final ArticleCreatedService articleCreatedService;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "article-created",
            groupId = "notification-consumer-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeActiveLimitChangedMessage(@Payload String message,
                                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                                 @Header(KafkaHeaders.OFFSET) int offset) throws JsonProcessingException {
        log.info("[START] consumeArticleCreatedMessage Message: {}, Partition: {}, Offset: {}", message, partition, offset);
        ArticleCreatedMessage messageAsObject = mapper.readValue(message, ArticleCreatedMessage.class);
        articleCreatedService.consume(messageAsObject);
        log.info("[END] consumeArticleCreatedMessage Message: {} Partition: {}, Offset: {}", message, partition, offset);
    }

    //@KafkaListener(topics = "articleCreated.retry}",
    //        groupId = "notification-consumer-group",
    //        containerFactory = "kafkaListenerContainerFactory")
    //public void consumeActiveLimitChangedRetryMessage(@Payload ArticleCreatedMessage message,
    //                                                  @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
    //                                                  @Header(KafkaHeaders.OFFSET) int offset) {
    //    log.info("[START] consumeArticleCreatedRetryMessage Message: {}, Partition: {}, Offset: {}", message, partition, offset);
    //    articleCreatedService.consume(message);
    //    log.info("[END] consumeArticleRetryMessage Message: {} Partition: {}, Offset: {}", message, partition, offset);
    //}
}
