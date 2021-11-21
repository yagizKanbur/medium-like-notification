package com.trendyol.articleservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import java.util.Date;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @IdPrefix
    private String prefix = "article";

    @Id
    @GeneratedValue(delimiter = "::" ,strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    private String authorId;

    @Field
    private String title;

    @Field
    private String data;

    @Field
    private String url;

    //version
    @Field
    private long creationDate;

    @Field
    private long lastUpdateDate;
}
