package com.example.olditemtradeplatform.like.service;

import com.example.olditemtradeplatform.like.domain.Like;
import com.example.olditemtradeplatform.like.domain.LikeId;
import com.example.olditemtradeplatform.like.dto.LikeResponseDTO;
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

    @Transactional
    public LikeResponseDTO createLike(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Like like = new Like(post, member);
        likeRepository.save(like);

        return LikeResponseDTO.from(like);
    }

    @Transactional(readOnly = true)
    public LikeResponseDTO findLike(Long postId, Long memberId) {
        Like like = likeRepository.findById(new LikeId(postId, memberId))
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));
        return LikeResponseDTO.from(like);
    }

    @Transactional
    public void deleteLike(Long postId, Long memberId) {
        likeRepository.deleteById(new LikeId(postId, memberId));
    }
}
