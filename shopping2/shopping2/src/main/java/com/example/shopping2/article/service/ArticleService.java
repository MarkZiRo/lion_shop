package com.example.shopping2.article.service;

import com.example.shopping2.Repository.UserRepository;
import com.example.shopping2.article.dto.ArticleDto;
import com.example.shopping2.article.entity.Article;
import com.example.shopping2.article.repository.ArticleRepository;
import com.example.shopping2.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleDto create(ArticleDto dto)
    {
//        UserDetails userDetails = (UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//        String username = userDetails.getUsername();
//
//        UserEntity writer = userRepository.findByUsername(username)
//                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserEntity writer = getUserEntity();

        Article newArticle = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .image(dto.getImage())
                .price(dto.getPrice())
                .writer(writer)
                .build();

        newArticle = articleRepository.save(newArticle);

        return ArticleDto.fromEntity(newArticle);

    }

    private UserEntity getUserEntity()
    {
        UserDetails userDetails = (UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ArticleDto> readAll()
    {
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article articleDto : articleRepository.findAll()) {
            articleDtos.add(ArticleDto.fromEntity(articleDto));
        }

        return articleDtos;
    }

    public ArticleDto readArticle(Long id)
    {
        return ArticleDto.fromEntity(articleRepository.findById(id).orElseThrow());
    }

    public void update(Long id, ArticleDto articleDto)
    {
        Article article = articleRepository.findById(id).orElseThrow();

        UserEntity writer = getUserEntity();

        article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .image(articleDto.getImage())
                .price(articleDto.getPrice())
                .writer(writer)
                .build();

    }

    public void delete(Long id)
    {
        Article article = articleRepository.findById(id).orElseThrow();

        articleRepository.delete(article);
    }


}
