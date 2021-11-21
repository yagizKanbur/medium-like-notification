package com.trendyol.notificationdispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationDispatcherApplication.class, args);
	}

}
