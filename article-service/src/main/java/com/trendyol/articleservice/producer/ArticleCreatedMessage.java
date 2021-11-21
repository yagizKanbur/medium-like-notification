package com.trendyol.articleservice.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ArticleCreatedMessage {
    private String authorId;
    private String title;
    private String url;
}
