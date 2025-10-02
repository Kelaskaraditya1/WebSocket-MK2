package com.starkIndustries.webSocket_Mk2.webSocket.model;

import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private String name;
    private String message;
    private MessageType messageType;

}
