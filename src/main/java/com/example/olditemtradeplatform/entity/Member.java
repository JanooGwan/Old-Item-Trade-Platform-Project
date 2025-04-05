package com.example.olditemtradeplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String userID;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    String email;
}
