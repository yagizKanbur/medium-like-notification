package com.trendyol.userservice.service;

import com.trendyol.userservice.exception.UserApiException;
import com.trendyol.userservice.model.dto.*;
import com.trendyol.userservice.model.entity.NotificationPreferences;
import com.trendyol.userservice.model.entity.User;
import com.trendyol.userservice.model.request.CreateUserRequest;
import com.trendyol.userservice.model.request.FollowRequest;
import com.trendyol.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    public static final String EXAMPLE_ID = "exampleId";
    public static final String FOLLOWER_ID = "followerId";
    public static final String SUBSCRIBED_ID = "subscribedId";
    public static final String EXAMPLE_MAIL = "example@mail.com";
    public static final String EXAMPLE_USERNAME = "exampleUser";

    @InjectMocks
    private UserService sut;

    @Mock
    private UserRepository repository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private User user;

    @BeforeEach
    void setUp() {
        List<String> followersList = new ArrayList<>();
        followersList.add(FOLLOWER_ID);

        List<String> subscribedList = new ArrayList<>();
        subscribedList.add(SUBSCRIBED_ID);

        user = User.builder()
                .userId(EXAMPLE_ID)
                .email(EXAMPLE_MAIL)
                .username(EXAMPLE_USERNAME)
                .notificationPreferences(NotificationPreferences.ALL)
                .subscribedIdsList(subscribedList)
                .followersIdsList(followersList)
                .build();

    }

    @Test
    void create_user_it_should_return_dto_when_happy_path() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username(EXAMPLE_USERNAME)
                .email(EXAMPLE_MAIL)
                .preferences(NotificationPreferences.ALL)
                .build();

        UserDto expected = UserDto.builder()
                .userId(EXAMPLE_ID)
                .username(EXAMPLE_USERNAME)
                .email(EXAMPLE_MAIL).build();

        when(repository.save(userArgumentCaptor.capture())).thenReturn(user);
        UserCreatedDto result = sut.createUser(request);

        User savedUser = userArgumentCaptor.getValue();

        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("userId")
                .ignoringFields("followersIdsList")
                .ignoringFields("subscribedIdsList")
                .isEqualTo(user);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("followersIdsList")
                .ignoringFields("subscribedIdsList")
                .isEqualTo(expected);
    }

    @Test
    void throw_user_api_exception_in_create_user_when_email_already_taken() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username(EXAMPLE_USERNAME)
                .email(EXAMPLE_MAIL)
                .preferences(NotificationPreferences.ALL)
                .build();

        when(repository.findUserByEmail(EXAMPLE_MAIL)).thenReturn(Optional.ofNullable(user));
        assertThrows(UserApiException.class, () -> sut.createUser(request));
    }

    @Test
    void getUserById_it_should_return_dto_when_happy_path() {
        List<String> followersList = new ArrayList<>();
        followersList.add(FOLLOWER_ID);

        List<String> subscribedList = new ArrayList<>();
        subscribedList.add(SUBSCRIBED_ID);

        UserDto expected = UserDto.builder()
                .userId(EXAMPLE_ID)
                .username(EXAMPLE_USERNAME)
                .email(EXAMPLE_MAIL)
                .notificationPreferences(NotificationPreferences.ALL)
                .followersIds(followersList)
                .subscribedIds(subscribedList)
                .build();

        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));

        UserDto result = sut.getUserById(EXAMPLE_ID);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void getUserById_throw_user_api_exception_when_id_not_found() {
        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.empty());
        assertThrows(UserApiException.class, () -> sut.getUserById(EXAMPLE_ID));
    }

    @Test
    void getFollowers_it_should_return_dto_when_happy_path() {
        List<String> followersList = new ArrayList<>();
        followersList.add(FOLLOWER_ID);

        FollowerListDto expected = FollowerListDto.builder()
                .follower(followersList)
                .build();

        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));
        FollowerListDto result = sut.getFollowers(EXAMPLE_ID);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getSubscribed_it_should_return_dto_when_happy_path() {
        List<String> subscribedList = new ArrayList<>();
        subscribedList.add(SUBSCRIBED_ID);

        FollowerListDto expected = FollowerListDto.builder()
                .follower(subscribedList)
                .build();

        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));
        FollowerListDto result = sut.getSubscribed(EXAMPLE_ID);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void followUser_it_should_follow_user_when_happy_path() {
        List<String> subscribedList = new ArrayList<>();
        subscribedList.add(SUBSCRIBED_ID);
        subscribedList.add(FOLLOWER_ID);

        FollowRequest request = FollowRequest.builder()
                .followedUserId(FOLLOWER_ID)
                .userId(EXAMPLE_ID)
                .build();

        UserFollowsDto expectedDto = UserFollowsDto.builder()
                .subscribed(subscribedList)
                .userId(EXAMPLE_ID)
                .build();

        when(repository.save(userArgumentCaptor.capture())).thenReturn(user);
        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));
        when(repository.findById(FOLLOWER_ID)).thenReturn(Optional.ofNullable(user));
        UserFollowsDto resultDto = sut.followUser(request);

        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);
        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void unfollowUser_it_should_unfollow_user_when_happy_path() {
        List<String> subscribedList = new ArrayList<>();

        FollowRequest request = FollowRequest.builder()
                .followedUserId(SUBSCRIBED_ID)
                .userId(EXAMPLE_ID)
                .build();

        UserFollowsDto expectedDto = UserFollowsDto.builder()
                .subscribed(subscribedList)
                .userId(EXAMPLE_ID)
                .build();

        when(repository.save(userArgumentCaptor.capture())).thenReturn(user);
        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));
        when(repository.findById(SUBSCRIBED_ID)).thenReturn(Optional.ofNullable(user));
        UserFollowsDto resultDto = sut.unfollowUser(request);

        User savedUser = userArgumentCaptor.getValue();
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);
        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void getPreferences_it_should_return_dto_when_happy_path(){
        UserPreferencesDto expectedDto = UserPreferencesDto.builder()
                .preferences(NotificationPreferences.ALL)
                .email(EXAMPLE_MAIL)
                .build();

        when(repository.findById(EXAMPLE_ID)).thenReturn(Optional.ofNullable(user));

        UserPreferencesDto resultDto = sut.getUserPreferences(EXAMPLE_ID);

        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void deleteUserById_throw_user_api_exception_when_user_not_deleted() {

    }


}