package com.trendyol.userservice.model.entity;

import com.trendyol.userservice.model.entity.NotificationPreferences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @IdPrefix
    private String prefix = "user";

    @Id
    @GeneratedValue(delimiter = "::" ,strategy = GenerationStrategy.UNIQUE)
    private String userId;

    @Field
    @NotNull
    @QueryIndexed
    private String email;

    @NotNull
    @Field
    private String username;

    @NotNull
    @Field
    private NotificationPreferences notificationPreferences;

    @NotNull
    @Field
    private List<String> followersIdsList;

    @NotNull
    @Field
    private List<String> subscribedIdsList;
}
