package com.trendyol.notificationdispatcher.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public <T> void sendMessage(String id, T message, String topic) {
        kafkaTemplate.send(topic, id, mapper.writeValueAsString(message));
    }
}
