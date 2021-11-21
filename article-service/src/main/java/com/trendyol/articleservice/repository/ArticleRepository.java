package com.trendyol.articleservice.repository;


import com.trendyol.articleservice.model.entity.Article;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CouchbaseRepository<Article, String> {

    List<Article> findArticleByAuthorId(String s);
}
