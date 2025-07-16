package com.example.olditemtradeplatform.like.repository;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.domain.LikeId;
import com.example.olditemtradeplatform.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    int countByPostId(Long postId);
    boolean existsById(LikeId id);

    @Query("SELECT l.post FROM Like l WHERE l.member.id = :memberId")
    List<Post> findLikedPostsByMember(Long memberId);
}
