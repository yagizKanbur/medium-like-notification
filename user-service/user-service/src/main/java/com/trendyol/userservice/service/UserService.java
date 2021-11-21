package com.trendyol.userservice.service;

import com.trendyol.userservice.exception.ErrorCode;
import com.trendyol.userservice.exception.UserApiException;
import com.trendyol.userservice.model.dto.*;
import com.trendyol.userservice.model.entity.NotificationPreferences;
import com.trendyol.userservice.model.entity.User;
import com.trendyol.userservice.model.request.CreateUserRequest;
import com.trendyol.userservice.model.request.FollowRequest;
import com.trendyol.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserCreatedDto createUser(CreateUserRequest request) {
        if(checkIfEmailRegistered(request.getEmail())){
            log.info("Email already has associated with a user. email: {}", request.getEmail());
            throw new UserApiException(ErrorCode.EMAIL_NOT_VALID, HttpStatus.BAD_REQUEST, "Email already has associated with a user. email: " + request.getEmail());
        }
        User newUser = buildUser(request);
        User savedUser = saveUserToDb(newUser);

        return UserCreatedDto.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .build();
    }

    private boolean checkIfEmailRegistered(String email) {
        Optional<User> optionalFoundUser = userRepository.findUserByEmail(email);
        return optionalFoundUser.isPresent();
    }

    private User buildUser(CreateUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .notificationPreferences(request.getPreferences())
                .followersIdsList(new ArrayList<>())
                .subscribedIdsList(new ArrayList<>())
                .build();
    }

    public DeleteUserDto deleteUser(String userId) {
        deleteUserById(userId);
        return DeleteUserDto.builder().message("User deleted").build();
    }

    private void deleteUserById(String userId){
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.info("User not deleted. userId: {}", userId);
            throw new UserApiException(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, "User not deleted userId: " + userId);
        }
    }

    public UserDto getUserById(String userId) {
        User foundUser = findUserById(userId);
        return UserDto.builder()
                .userId(foundUser.getUserId())
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .followersIds(foundUser.getFollowersIdsList())
                .subscribedIds(foundUser.getSubscribedIdsList())
                .notificationPreferences(foundUser.getNotificationPreferences())
                .build();
    }

    public FollowerListDto getFollowers(String userId) {
        User foundUser = findUserById(userId);
        return FollowerListDto.builder().follower(foundUser.getFollowersIdsList()).build();
    }

    public FollowerListDto getSubscribed(String userId) {
        User foundUser = findUserById(userId);
        return FollowerListDto.builder().follower(foundUser.getSubscribedIdsList()).build();
    }

    private User findUserById(String userId) {
        Optional<User> optionalFoundUser = userRepository.findById(userId);
        if (optionalFoundUser.isEmpty()) {
            log.info("User not found. userId: {}", userId);
            throw new UserApiException(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST, "User Not Found, userId:" + userId);
        }
        return optionalFoundUser.get();
    }

    public UserFollowsDto followUser(FollowRequest request) {
        User foundUser = findUserById(request.getUserId());
        User followedUser = findUserById(request.getFollowedUserId());

        if(foundUser.getSubscribedIdsList().contains(request.getFollowedUserId())){
            log.info("User already followed. userId: " + request.getUserId() + " followedId: " + request.getFollowedUserId());
            throw new UserApiException(ErrorCode.USER_ALREADY_FOLLOWED, HttpStatus.BAD_REQUEST, "User already followed. userId: " + request.getUserId() + " followedId: " + request.getFollowedUserId());
        }
        foundUser.getSubscribedIdsList().add(request.getFollowedUserId());
        followedUser.getFollowersIdsList().add(request.getUserId());

        User userThatSendFollowRequest = saveUserToDb(foundUser);
        saveUserToDb(followedUser);

        return UserFollowsDto.builder()
                .userId(userThatSendFollowRequest.getUserId())
                .subscribed(userThatSendFollowRequest.getSubscribedIdsList())
                .build();
    }

    public UserFollowsDto unfollowUser(FollowRequest request) {
        User foundUser = findUserById(request.getUserId());
        User unfollowedUser = findUserById(request.getFollowedUserId());

        foundUser.getSubscribedIdsList().removeIf(id -> id.equals(request.getFollowedUserId()));
        unfollowedUser.getFollowersIdsList().removeIf(id -> id.equals(request.getUserId()));

        User savedUser = saveUserToDb(foundUser);
        saveUserToDb(unfollowedUser);

        return UserFollowsDto.builder()
                .userId(savedUser.getUserId())
                .subscribed(savedUser.getSubscribedIdsList())
                .build();
    }

    private User saveUserToDb(User user){
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("User not saved. Exception:{}, User{} ", e.getMessage(), user);
            throw new UserApiException(ErrorCode.USER_NOT_SAVED, HttpStatus.BAD_REQUEST, "User not saved. userId" + user.getUsername() + "Exception: {}", e.getMessage());
        }
    }

    public UserPreferencesDto getUserPreferences(String userId) {
        User foundUser = findUserById(userId);
        NotificationPreferences userPreferences = foundUser.getNotificationPreferences();
        return UserPreferencesDto.builder()
                .preferences(userPreferences)
                .email(foundUser.getEmail())
                .build();
    }
}
