package com.starkIndustries.webSocket_Mk2.webSocket.controller;

import com.starkIndustries.webSocket_Mk2.keys.Keys;
import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import com.starkIndustries.webSocket_Mk2.webSocket.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/send/message")
    @SendTo("/topic/public/messages")
    public Message sendMessage(
            @Payload Message message
    ){
        return message;
    }
}
