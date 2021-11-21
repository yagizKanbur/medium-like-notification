package com.trendyol.articleservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdatedDto extends BaseDto {
    private String articleId;
    private Long lastUpdateDate;
}

