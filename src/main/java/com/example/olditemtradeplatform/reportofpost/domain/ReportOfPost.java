package com.example.olditemtradeplatform.reportofpost.domain;

import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportOfPost {

    @EmbeddedId
    ReportOfPostId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reporterId")
    @JoinColumn(name = "reporter_id", nullable = false)
    Member reporter;

    @Lob
    @Column(nullable = false)
    String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime reportedDate;

    public ReportOfPost(Post post, Member reporter, String content) {
        this.post = post;
        this.reporter = reporter;
        this.id = new ReportOfPostId(post.getId(), reporter.getId());
        this.content = content;
        this.reportedDate = LocalDateTime.now();
    }
}
