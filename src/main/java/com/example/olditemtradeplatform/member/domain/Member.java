package com.example.olditemtradeplatform.member.domain;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.like.domain.Like;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    @Column(nullable = false)
    boolean isSuspended;

    @Column(nullable = false)
    LocalDate suspendUntil;

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


    public boolean isMemberSuspended(Member member) {
        return member.isSuspended() && member.getSuspendUntil().isAfter(LocalDate.now());
    }
}
