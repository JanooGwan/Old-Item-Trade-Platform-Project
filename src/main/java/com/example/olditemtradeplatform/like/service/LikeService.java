package com.example.olditemtradeplatform.like.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.domain.LikeId;
import com.example.olditemtradeplatform.like.dto.LikeResponseDTO;
import com.example.olditemtradeplatform.like.exception.LikeErrorCode;
import com.example.olditemtradeplatform.like.repository.LikeRepository;
import com.example.olditemtradeplatform.member.domain.Member;
import com.example.olditemtradeplatform.member.repository.MemberRepository;
import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;



    @Transactional(readOnly = true)
    public LikeResponseDTO findLike(Long postId, Long memberId) {
        Like like = likeRepository.findById(new LikeId(postId, memberId))
                .orElseThrow(() -> new CustomException(LikeErrorCode.LIKE_NOT_FOUND));
        return LikeResponseDTO.from(like);
    }

    @Transactional(readOnly = true)
    public int countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Transactional(readOnly = true)
    public boolean isPostLikedByMember(Long postId, Member member) {
        LikeId likeId = new LikeId(postId, member.getId());
        return likeRepository.existsById(likeId);
    }

    @Transactional
    public LikeResponseDTO toggleLike(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(LikeErrorCode.POST_NOT_FOUND));

        LikeId likeId = new LikeId(postId, member.getId());

        if (likeRepository.existsById(likeId)) {
            likeRepository.deleteById(likeId);
            post.decreaseLikeCount();
            return LikeResponseDTO.of(postId, member.getId(), false, post.getLikeCount());
        } else {
            Like like = new Like(post, member);
            likeRepository.save(like);
            post.increaseLikeCount();
            return LikeResponseDTO.of(postId, member.getId(), true, post.getLikeCount());
        }
    }

    @Transactional
    public void deleteLike(Long postId, Long memberId) {
        likeRepository.deleteById(new LikeId(postId, memberId));
    }
}
