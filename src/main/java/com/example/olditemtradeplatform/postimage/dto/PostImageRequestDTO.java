package com.example.olditemtradeplatform.postimage.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostImageRequestDTO {

    @Positive
    @NotNull(message = "게시글 ID는 필수입니다.")
    Long postId;

    @NotNull(message = "이미지 파일은 필수입니다.")
    MultipartFile file;
}
