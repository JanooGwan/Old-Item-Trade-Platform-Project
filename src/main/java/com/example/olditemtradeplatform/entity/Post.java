package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member writer;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String content;

    @Column
    LocalDateTime createDate;

    @Column
    LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DealStatus dealStatus;
}
