package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn("post_id")
    Post post;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Long count;

    @Column(nullable = false)
    Long price;
}
