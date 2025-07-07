package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, updatable = false)
    String userId;

    @NotBlank
    @Column(nullable = false)
    String password;

    @NotBlank
    @Column(nullable = false, unique = true)
    String nickname;

    @Email
    @Column(nullable = false, unique = true)
    String email;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> member1Rooms = new ArrayList<>();

    @OneToMany(mappedBy = "member2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> member2Rooms = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true) // 식별 관계
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReportOfPost> reportOfPosts = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;
}
