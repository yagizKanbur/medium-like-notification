package com.trendyol.notificationdispatcher.consumer.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FollowersListMessage {
    private String authorId;
    private String title;
    private String url;
    private List<String> followersList;
}
