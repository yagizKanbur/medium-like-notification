package com.trendyol.articleservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trendyol.articleservice.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ArticleApiResponse<T extends BaseDto> {
    private T data;
    private ErrorResponse error;
}
