package com.trendyol.notificationdispatcher.service;

import com.trendyol.notificationdispatcher.client.response.NotificationPreferences;
import com.trendyol.notificationdispatcher.producer.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class NotificationPreferenceServiceFactory {

    private final Supplier<Stream<NotificationPreferenceService>> services;

    public NotificationPreferenceServiceFactory(List<NotificationPreferenceService> services) {
        this.services = services::stream;
    }

    public void process(NotificationPreferences notificationPreferences, NotificationMessage message){
        services.get()
                .filter(service -> service.accept(notificationPreferences))
                .forEach(i->i.execute(notificationPreferences, message));
    }
}
