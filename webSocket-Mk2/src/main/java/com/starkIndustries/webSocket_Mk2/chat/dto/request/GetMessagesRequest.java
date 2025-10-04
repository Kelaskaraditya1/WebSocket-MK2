package com.starkIndustries.webSocket_Mk2.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMessagesRequest {

    private String senderId;
    private String receiverId;
}
