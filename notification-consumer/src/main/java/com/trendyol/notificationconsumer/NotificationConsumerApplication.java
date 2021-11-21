package com.trendyol.notificationconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationConsumerApplication.class, args);
	}

}
