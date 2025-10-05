package com.starkIndustries.webSocket_Mk2.chat.controller;

import com.starkIndustries.webSocket_Mk2.chat.dto.request.GetMessagesRequest;
import com.starkIndustries.webSocket_Mk2.chat.dto.request.MessageRequest;
import com.starkIndustries.webSocket_Mk2.chat.model.ChatMessage;
import com.starkIndustries.webSocket_Mk2.chat.service.ChatMessageService;
import com.starkIndustries.webSocket_Mk2.keys.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.KeyFactorySpi;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    public ChatMessageService chatMessageService;

    @Autowired
    public SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/get/chat/messages")
    public ResponseEntity<?> getChatMessages(
            @RequestBody GetMessagesRequest getMessagesRequest
            ){
        Map<String, Object> response = new HashMap<>();
        response.put(Keys.TIME_STAMP, Instant.now());

        if(getMessagesRequest!=null){

            List<ChatMessage> chatMessages = this.chatMessageService.getChatMessages(
                    getMessagesRequest.getSenderId(),
                    getMessagesRequest.getReceiverId()
            );

            if(!chatMessages.isEmpty()){
                response.put(Keys.STATUS, HttpStatus.OK);
                response.put(Keys.STATUS_CODE,HttpStatus.OK.value());
                response.put(Keys.MESSAGE, "Chat messages retrieved successfully");
                response.put("chatMessages", chatMessages);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.put(Keys.STATUS, HttpStatus.OK);
                response.put(Keys.STATUS_CODE,HttpStatus.OK.value());
                response.put(Keys.MESSAGE, "No chat messages found");
                response.put("chatMessages", chatMessages);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response.put(Keys.STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        response.put(Keys.STATUS_CODE,HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(Keys.MESSAGE, "Invalid request");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @MessageMapping("/chat/send/message")
    public void processMessage(
            @Payload MessageRequest messageRequest
            ){
        if(messageRequest!=null){

            ChatMessage chatMessage1 = this.chatMessageService.saveMessage(messageRequest);
            log.info("chatMessage: {}",chatMessage1);

            if(chatMessage1!=null){

                simpMessagingTemplate.convertAndSendToUser(
                        chatMessage1.getReceiverId(),
                        "/queue/messages",
                        chatMessage1
                );
            }
        }


    }

}
