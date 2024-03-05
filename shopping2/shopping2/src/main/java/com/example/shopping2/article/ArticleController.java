package com.example.shopping2.article;

import com.example.shopping2.article.dto.ArticleDto;
import com.example.shopping2.article.entity.Article;
import com.example.shopping2.article.repository.ArticleRepository;
import com.example.shopping2.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto dto)
    {
        return articleService.create(dto);
    }

    @GetMapping("read/{id}")
    public ArticleDto readArticle(@PathVariable("id") Long id)
    {
      //  model.addAttribute("article", articleService.readArticle(id));

        return articleService.readArticle(id);
    }

    @GetMapping("read")
    public List<ArticleDto> readAll()
    {
        return articleService.readAll();
    }


    @GetMapping("{id}/edit")
    public ArticleDto editArticle(@RequestBody ArticleDto dto)
    {
        articleService.update(dto.getId(), dto);

        return readArticle(dto.getId());
    }

    @GetMapping("{id}/delete")
    public void deleteArticle(@PathVariable("id") Long id)
    {
        articleService.delete(id);

    }

    @GetMapping("suggest/{id}")
    public void suggestArticle(@PathVariable("id") Long id)
    {
        ArticleDto articleDto = articleService.readArticle(id);
    }
}
