package com.example.olditemtradeplatform.reportofpost.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ReportOfPostId implements Serializable {

    private Long postId;
    private Long reporterId;
}