package com.trendyol.articleservice.service;

import com.trendyol.articleservice.exception.ArticleApiException;
import com.trendyol.articleservice.exception.ErrorCode;
import com.trendyol.articleservice.model.dto.ArticleCreatedDto;
import com.trendyol.articleservice.model.dto.ArticleDeletedDto;
import com.trendyol.articleservice.model.dto.ArticleDto;
import com.trendyol.articleservice.model.dto.ArticleUpdatedDto;
import com.trendyol.articleservice.model.entity.Article;
import com.trendyol.articleservice.model.request.CreateArticleRequest;
import com.trendyol.articleservice.model.request.DeleteArticleRequest;
import com.trendyol.articleservice.model.request.UpdateArticleRequest;
import com.trendyol.articleservice.producer.ArticleCreatedMessage;
import com.trendyol.articleservice.producer.KafkaProducer;
import com.trendyol.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final KafkaProducer kafkaProducer;

    public ArticleCreatedDto createArticle(CreateArticleRequest request) {
        Article newArticle = buildArticle(request);
        Article savedArticle = saveArticleToDb(newArticle);
        ArticleCreatedMessage message = ArticleCreatedMessage.builder()
                .authorId(savedArticle.getAuthorId())
                .title(savedArticle.getTitle())
                .url(savedArticle.getUrl())
                .build();
        log.info("message send:{}",message);
        kafkaProducer.sendMessage(message.getAuthorId(),message,"article-created");

        return ArticleCreatedDto.builder()
                .articleId(savedArticle.getId())
                .title(savedArticle.getTitle())
                .url(savedArticle.getUrl())
                .createdDate(savedArticle.getCreationDate())
                .build();
    }

    private Article buildArticle(CreateArticleRequest request) {
        return Article.builder()
                .authorId(request.getAuthorId())
                .title(request.getTitle())
                .data(request.getData())
                .url(generateUrl(request.getTitle(), request.getAuthorId()))
                .creationDate(new Date().getTime())
                .lastUpdateDate(new Date().getTime())
                .build();
    }

    private Article saveArticleToDb(Article article) {
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            log.error("Article not saved. Exception:{}, Article{} ", e.getMessage(), article);
            throw new ArticleApiException(ErrorCode.ARTICLE_NOT_SAVED, HttpStatus.BAD_REQUEST, "Article not saved. Article: {}" + article.getTitle() + "Exception: {},", e.getMessage());
        }
    }

    public ArticleUpdatedDto updateArticle(UpdateArticleRequest request) {
        //instant
        Article foundArticle = findArticleById(request.getArticleId());
        foundArticle.setTitle(request.getTitle());
        foundArticle.setData(request.getData());
        foundArticle.setLastUpdateDate(new Date().getTime());

        Article updatedArticle = saveArticleToDb(foundArticle);
        return ArticleUpdatedDto.builder()
                .articleId(updatedArticle.getId())
                .lastUpdateDate(updatedArticle.getLastUpdateDate())
                .build();
    }

    public ArticleDto getArticle(String articleId) {
        Article foundArticle = findArticleById(articleId);
        return ArticleDto.builder()
                .id(foundArticle.getId())
                .authorId(foundArticle.getAuthorId())
                .title(foundArticle.getTitle())
                .data(foundArticle.getData())
                .url(foundArticle.getUrl())
                .creationDate(foundArticle.getCreationDate())
                .lastUpdateDate(foundArticle.getLastUpdateDate())
                .build();
    }

    public ArticleDeletedDto deleteArticle(DeleteArticleRequest request) {
        deleteArticle(request.getArticleId());
        return ArticleDeletedDto.builder().message("Article deleted").build();
    }

    private void deleteArticle(String articleId) {
        try {
            articleRepository.deleteById(articleId);
        } catch (Exception e) {
            log.info("User Not Deleted Id: {}", articleId);
            throw new ArticleApiException(ErrorCode.ARTICLE_NOT_FOUND, HttpStatus.BAD_REQUEST, "User Not Deleted Id: " + articleId);
        }
    }

    private Article findArticleById(String articleId) {
        Optional<Article> optionalFoundArticle = articleRepository.findById(articleId);
        if (optionalFoundArticle.isEmpty()) {
            log.info("User Not Found Id: {}", articleId);
            throw new ArticleApiException(ErrorCode.ARTICLE_NOT_FOUND, HttpStatus.BAD_REQUEST, "Article Not found articleId:" + articleId);
        }
        return optionalFoundArticle.get();
    }

    private String generateUrl(String title, String authorId) {
         title = title.replaceAll(" ", "-");
         title = title.toLowerCase();
         return  "www.cakmamedium.com/"+ authorId.substring(0,8) + "/" + title;
    }
}
