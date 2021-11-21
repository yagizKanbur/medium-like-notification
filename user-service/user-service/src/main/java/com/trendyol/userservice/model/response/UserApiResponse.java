package com.trendyol.userservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trendyol.userservice.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserApiResponse<T extends BaseDto> {
    private T data;
    private ErrorResponse error;
}
