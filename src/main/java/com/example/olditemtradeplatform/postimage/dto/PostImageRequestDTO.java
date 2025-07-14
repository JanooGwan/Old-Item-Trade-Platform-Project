package com.example.olditemtradeplatform.postimage.dto;

import com.example.olditemtradeplatform.post.domain.Post;
import com.example.olditemtradeplatform.postimage.domain.PostImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostImageRequestDTO {

    Long postId;
    MultipartFile file;
}
