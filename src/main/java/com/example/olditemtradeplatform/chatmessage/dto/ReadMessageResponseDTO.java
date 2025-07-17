package com.example.olditemtradeplatform.chatmessage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReadMessageResponseDTO {

    private Long chatRoomId;
    private Long readerId;
}
