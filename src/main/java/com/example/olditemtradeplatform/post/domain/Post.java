package com.example.olditemtradeplatform.post.domain;

import com.example.olditemtradeplatform.postimage.domain.PostImage;
import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member writer;

    @NotBlank
    @Column(nullable = false, updatable = false)
    String title;

    @Lob
    @NotBlank
    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    Long likeCount = 0L;

    @Column(nullable = false)
    Long viewCount = 0L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createDate;

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    List<PostImage> postImages = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DealStatus dealStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DealWay dealWay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BuyOrSale buyOrSale;

    public void updatePost(String content, DealWay dealWay, DealStatus dealStatus) {
        this.content = content;
        this.dealWay = dealWay;
        this.dealStatus = dealStatus;
    }
}
