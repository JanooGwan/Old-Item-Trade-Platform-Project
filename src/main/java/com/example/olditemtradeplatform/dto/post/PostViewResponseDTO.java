package com.example.olditemtradeplatform.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostViewResponseDTO {

    private final String writerName;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;
    private final LocalDateTime modifiedDate;

    public PostViewResponseDTO(String writerName, String title, String content,
            LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.writerName = writerName;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
