package com.trendyol.userservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String followedUserId;
}
