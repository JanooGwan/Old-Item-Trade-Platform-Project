package com.example.olditemtradeplatform.member.domain;

import com.example.olditemtradeplatform.chatroom.domain.ChatRoom;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.like.domain.Like;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 20)
    String userId;

    @Column(nullable = false, length = 100)
    String password;

    @Column(nullable = false)
    boolean isSuspended;

    @Column(length = 500)
    String suspendReason;

    @Column
    LocalDate suspendUntil;

    @Column(nullable = false, unique = true, length = 10)
    String nickname;

    @Column(nullable = false, unique = true, length = 500)
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


    public void updateMember(String encryptedPassword, String email, String nickname) {
        this.password = encryptedPassword;
        this.email = email;
        this.nickname = nickname;
    }

    public boolean isSuspendedNow() {
        return this.isSuspended && this.suspendUntil != null && this.suspendUntil.isAfter(LocalDate.now());
    }

    // 정지 시
    public void updateSuspendInfo(boolean isSuspended, LocalDate until, String reason) {
        this.isSuspended = isSuspended;
        this.suspendUntil = until;
        this.suspendReason = reason;
    }

    // 정지 해제 시
    public void updateSuspendInfo(boolean isSuspended, LocalDate until) {
        this.isSuspended = isSuspended;
        this.suspendUntil = until;
        this.suspendReason = null;
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}
