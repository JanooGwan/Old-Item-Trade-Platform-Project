package com.example.olditemtradeplatform.postimage.controller;

import com.example.olditemtradeplatform.postimage.domain.PostImageId;
import com.example.olditemtradeplatform.postimage.dto.PostImageRequestDTO;
import com.example.olditemtradeplatform.postimage.dto.PostImageResponseDTO;
import com.example.olditemtradeplatform.postimage.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class PostImageController implements PostImageApi {

    private final PostImageService postImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImageResponseDTO> uploadPostImage(
            @ModelAttribute PostImageRequestDTO dto
    ) {
        return ResponseEntity.ok(postImageService.saveImage(dto));
    }

    @GetMapping("/{postId}/{imageAt}")
    public ResponseEntity<PostImageResponseDTO> getImage(
            @PathVariable Long postId,
            @PathVariable Long imageAt
    ) {
        PostImageId imageId = new PostImageId(postId, imageAt);
        PostImageResponseDTO image = postImageService.findImage(imageId);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/{postId}/{imageAt}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long postId,
            @PathVariable Long imageAt
    ) {
        PostImageId imageId = new PostImageId(postId, imageAt);
        postImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
