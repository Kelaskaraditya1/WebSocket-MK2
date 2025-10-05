package com.starkIndustries.webSocket_Mk2.chat.service;

import com.starkIndustries.webSocket_Mk2.chat.dto.request.GetMessagesRequest;
import com.starkIndustries.webSocket_Mk2.chat.dto.request.MessageRequest;
import com.starkIndustries.webSocket_Mk2.chat.model.ChatMessage;
import com.starkIndustries.webSocket_Mk2.chat.repository.ChatMessageRepository;
import com.starkIndustries.webSocket_Mk2.chatRoom.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatMessageService {

    @Autowired
    public ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatRoomService chatRoomService;

    public ChatMessage saveMessage(MessageRequest messageRequest){

        if(messageRequest!=null){

            String chatRoomId = this.chatRoomService
                    .getChatRoomId(messageRequest.getSenderId(),messageRequest.getReceiverId(),true)
                    .get();

            log.info("chatRoomId:{}",chatRoomId);

            ChatMessage chatMessage = ChatMessage.builder()
                    .id(UUID.randomUUID().toString())
                    .chatId(chatRoomId)
                    .senderId(messageRequest.getSenderId())
                    .receiverId(messageRequest.getReceiverId())
                    .message(messageRequest.getMessage())
                    .timeStamp(Instant.now())
                    .build();


            return this.chatMessageRepository.save(chatMessage);
        }

        return null;

    }

    public List<ChatMessage> getChatMessages(String senderId,String receiverId){

        String chatRoomId = this.chatRoomService.getChatRoomId(senderId,receiverId,false)
                .get();
        log.debug("ChatRoomId:{}",chatRoomId);

        List<ChatMessage> chatMessages = this.chatMessageRepository.findAll()
                .stream()
                .filter(chatMessage->chatMessage.getChatId().equals(chatRoomId))
                .collect(Collectors.toList());

        if(!chatMessages.isEmpty())
            return chatMessages;

        return new ArrayList<>(Collections.emptyList());
    }


}
