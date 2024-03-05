package com.example.shopping2.article.repository;

import com.example.shopping2.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}

