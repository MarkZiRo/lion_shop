package com.example.shopping2.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Getter
@Table(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    private String nickname;
    private String name;
    private int age;
    private String email;
    private String phone;

    private String authorities;
}
