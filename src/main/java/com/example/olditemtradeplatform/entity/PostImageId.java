package com.example.olditemtradeplatform.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PostImageId implements Serializable {

    private Long postId;
    private Long imageAt;
}