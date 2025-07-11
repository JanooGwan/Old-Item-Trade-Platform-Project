package com.example.olditemtradeplatform.chatmessage.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ChatMessageId implements Serializable {

    private Long chatRoomId;
    private Long sentAt;
}
