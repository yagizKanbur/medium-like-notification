package com.trendyol.notificationconsumer.consumer.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ArticleCreatedMessage{
    private String authorId;
    private String title;
    private String url;

}
