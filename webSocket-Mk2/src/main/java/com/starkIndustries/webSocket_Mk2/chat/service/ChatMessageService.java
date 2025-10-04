package com.starkIndustries.webSocket_Mk2.chat.service;

import com.starkIndustries.webSocket_Mk2.chat.model.ChatMessage;
import com.starkIndustries.webSocket_Mk2.chat.repository.ChatMessageRepository;
import com.starkIndustries.webSocket_Mk2.chatRoom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    public ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatRoomService chatRoomService;

    public ChatMessage saveMessage(ChatMessage chatMessage){

        if(chatMessage!=null){

            String chatRoomId = this.chatRoomService
                    .getChatRoomId(chatMessage.getSenderId(),chatMessage.getReceiverId(),true)
                    .get();

            chatMessage.setChatId(chatRoomId);
            chatMessage.setTimeStamp(Instant.now());

            return this.chatMessageRepository.save(chatMessage);
        }

        return null;

    }

    public List<ChatMessage> getChatMessages(String senderId,String receiverId){

        String chatRoomId = this.chatRoomService.getChatRoomId(senderId,receiverId,false)
                .get();

        List<ChatMessage> chatMessages = this.chatMessageRepository.findAll()
                .stream()
                .filter(chatMessage->chatMessage.getChatId().equals(chatRoomId))
                .collect(Collectors.toList());

        if(!chatMessages.isEmpty())
            return chatMessages;

        return new ArrayList<>(Collections.emptyList());
    }




}
