package com.example.shopping2.article.entity;

import com.example.shopping2.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String content;

    private String image;

    private Integer price;

    @Setter
    @ManyToOne
    private UserEntity writer;

}
