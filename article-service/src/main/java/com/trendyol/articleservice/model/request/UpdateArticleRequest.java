package com.trendyol.articleservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private String articleId;
    @NotBlank
    private String title;
    @NotBlank
    private String data;
}
