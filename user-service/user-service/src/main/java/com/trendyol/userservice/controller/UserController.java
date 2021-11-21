package com.trendyol.userservice.controller;

import com.trendyol.userservice.model.dto.*;
import com.trendyol.userservice.model.request.CreateUserRequest;
import com.trendyol.userservice.model.request.FollowRequest;
import com.trendyol.userservice.model.response.UserApiResponse;
import com.trendyol.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public UserApiResponse<UserCreatedDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("[START]::CreateUser endpoint starting");
        UserCreatedDto userCreatedDto = userService.createUser(request);
        log.info("[END]::CreateUser endpoint ending");
        return UserApiResponse.<UserCreatedDto>builder().data(userCreatedDto).build();
    }

    @DeleteMapping("/{userId}")
    public UserApiResponse<DeleteUserDto> deleteUser(@PathVariable String userId) {
        log.info("[START]::DeleteUser endpoint starting");
        DeleteUserDto deleteUserDto = userService.deleteUser(userId);
        log.info("[START]::DeleteUser endpoint ending");
        return UserApiResponse.<DeleteUserDto>builder().data(deleteUserDto).build();
    }

    @GetMapping("/{userId}")
    public UserApiResponse<UserDto> getUserById(@PathVariable String userId) {
        log.info("[START]::getUserById endpoint starting");
        UserDto userDto = userService.getUserById(userId);
        log.info("[END]::getUserById endpoint ending");
        return UserApiResponse.<UserDto>builder().data(userDto).build();
    }

    @GetMapping("/{userId}/followers")
    public UserApiResponse<FollowerListDto> getUserFollowersById(@PathVariable String userId) {
        log.info("[START]::getFollowers endpoint starting");
        FollowerListDto followerListDto = userService.getFollowers(userId);
        log.info("[END]::getFollowers endpoint ending");
        return UserApiResponse.<FollowerListDto>builder().data(followerListDto).build();
    }

    @GetMapping("/{userId}/subscribed") //
    public UserApiResponse<FollowerListDto> getFollowedUsersById(@PathVariable String userId) {
        log.info("[START]::getSubscribed endpoint starting");
        FollowerListDto followerListDto = userService.getSubscribed(userId);
        log.info("[END]::getSubscribed endpoint ending");
        return UserApiResponse.<FollowerListDto>builder().data(followerListDto).build();
    }

    @PutMapping("/{userId}/follow/{subscribedId}")
    public UserApiResponse<UserFollowsDto> followUser(@Valid @PathVariable String userId, @PathVariable String subscribedId) {
        FollowRequest request = FollowRequest.builder().userId(userId).followedUserId(subscribedId).build();
        log.info("[START]::followUser endpoint starting");
        UserFollowsDto userFollowsDto = userService.followUser(request);
        log.info("[END]::followUser endpoint ending");
        return UserApiResponse.<UserFollowsDto>builder().data(userFollowsDto).build();
    }

    @PutMapping("/{userId}/unfollow/{unsubscribedId}")
    public UserApiResponse<UserFollowsDto> unfollowUser(@Valid @PathVariable String userId, @PathVariable String unsubscribedId) {
        FollowRequest request = FollowRequest.builder().userId(userId).followedUserId(unsubscribedId).build();
        log.info("[START]::unfollowUser endpoint starting");
        UserFollowsDto userFollowsDto = userService.unfollowUser(request);
        log.info("[END]::unfollowUser endpoint ending");
        return UserApiResponse.<UserFollowsDto>builder().data(userFollowsDto).build();
    }

    @GetMapping("/{userId}/preferences") // TODO: 16.11.2021 Bunun gibi
    public UserApiResponse<UserPreferencesDto> getUsersPreferences(@Valid @PathVariable String userId) {
        log.info("[START]::getUserPref endpoint starting");
        UserPreferencesDto userPrefDto = userService.getUserPreferences(userId);
        log.info("[END]::getUserPref endpoint ending");
        return UserApiResponse.<UserPreferencesDto>builder().data(userPrefDto).build();
    }
}
