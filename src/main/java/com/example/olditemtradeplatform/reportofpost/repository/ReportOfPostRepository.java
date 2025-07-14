package com.example.olditemtradeplatform.reportofpost.repository;

import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPost;
import com.example.olditemtradeplatform.reportofpost.domain.ReportOfPostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportOfPostRepository extends JpaRepository<ReportOfPost, ReportOfPostId> {
    boolean existsById(ReportOfPostId id);
}
