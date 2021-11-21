package com.trendyol.articleservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto extends BaseDto {
    private String id;
    private String authorId;
    private String title;
    private String data;
    private String url;
    private Long creationDate;
    private Long lastUpdateDate;
}
