package com.trendyol.articleservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreatedDto extends BaseDto {
    private String articleId;
    private String title;
    private Long createdDate;
    private String url;
}
