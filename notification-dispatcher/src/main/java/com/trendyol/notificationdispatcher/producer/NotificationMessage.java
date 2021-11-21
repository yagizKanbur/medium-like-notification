package com.trendyol.notificationdispatcher.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NotificationMessage {
    private String authorId;
    private String title;
    private String url;
    private String receiverId;
    private String email;
}
