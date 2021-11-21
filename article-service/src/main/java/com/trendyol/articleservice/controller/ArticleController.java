package com.trendyol.articleservice.controller;

import com.trendyol.articleservice.model.dto.ArticleCreatedDto;
import com.trendyol.articleservice.model.dto.ArticleDeletedDto;
import com.trendyol.articleservice.model.dto.ArticleDto;
import com.trendyol.articleservice.model.dto.ArticleUpdatedDto;
import com.trendyol.articleservice.model.request.CreateArticleRequest;
import com.trendyol.articleservice.model.request.DeleteArticleRequest;

import com.trendyol.articleservice.model.request.UpdateArticleRequest;
import com.trendyol.articleservice.model.response.ArticleApiResponse;
import com.trendyol.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping()
    public ArticleApiResponse<ArticleCreatedDto> createArticle(@Valid @RequestBody CreateArticleRequest request){
        log.info("[START]::CreateArticle endpoint starting"); // TODO: 16.11.2021 request'i logla
        ArticleCreatedDto articleCreatedDto = articleService.createArticle(request);
        log.info("[END]::CreateArticle endpoint ending"); // TODO: 16.11.2021 responsu ve requesti logla
        return ArticleApiResponse.<ArticleCreatedDto>builder().data(articleCreatedDto).build();
    }

    @PutMapping("/{articleId}")
    public ArticleApiResponse<ArticleUpdatedDto> updateArticle (@Valid @RequestBody UpdateArticleRequest request) {
        log.info("[START]::UpdateArticle endpoint starting");
        ArticleUpdatedDto articleUpdatedDto = articleService.updateArticle(request);
        log.info("[END]::UpdateArticle endpoint ending");
        return ArticleApiResponse.<ArticleUpdatedDto>builder().data(articleUpdatedDto).build();
    }

    @GetMapping("/{articleId}")
    public ArticleApiResponse<ArticleDto> getArticle (@PathVariable("articleId") String articleId){
        log.info("[START]::GetArticle endpoint starting");
        ArticleDto articleDto = articleService.getArticle(articleId);
        log.info("[END]::UpdateArticle endpoint ending");
        return ArticleApiResponse.<ArticleDto>builder().data(articleDto).build();
    }

    @DeleteMapping()
    public ArticleApiResponse<ArticleDeletedDto> deleteArticle (@Valid @RequestBody DeleteArticleRequest request) {
        log.info("[START]::DeleteArticle endpoint starting");
        ArticleDeletedDto articleDeletedDto = articleService.deleteArticle(request);
        log.info("[END]::DeleteArticle endpoint ending");
        return ArticleApiResponse.<ArticleDeletedDto>builder().data(articleDeletedDto).build();
    }
}
