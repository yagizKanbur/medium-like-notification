package com.trendyol.notificationconsumer.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NotificationInfo {
    private String authorId;
    private String title;
    private String url;
    private List<String> followersList;
}
