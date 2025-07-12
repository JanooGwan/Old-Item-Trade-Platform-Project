package com.example.olditemtradeplatform.product.domain;

import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false)
    Long count;

    @Column(nullable = false)
    Long price;
}
