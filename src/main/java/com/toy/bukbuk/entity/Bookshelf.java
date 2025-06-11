package com.toy.bukbuk.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bookshelf {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String colorCode;

    private String icon; // ex) "💻"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
