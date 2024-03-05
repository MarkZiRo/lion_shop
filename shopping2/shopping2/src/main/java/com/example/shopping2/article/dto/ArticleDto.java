package com.example.shopping2.article.dto;

import com.example.shopping2.article.entity.Article;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;

    @Setter
    private String title;

    @Setter
    private String content;

    @Setter
    private String image;

    @Setter
    private Integer price;

    @Setter
    private String writer;

    public static ArticleDto fromEntity(Article entity)
    {
        return ArticleDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .image(entity.getImage())
                .price(entity.getPrice())
                .writer(entity.getWriter().getUsername())
                .build();
    }

}
