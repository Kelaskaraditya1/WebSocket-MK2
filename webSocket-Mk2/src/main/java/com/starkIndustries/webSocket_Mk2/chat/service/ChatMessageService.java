package com.starkIndustries.webSocket_Mk2.chat.service;

import com.starkIndustries.webSocket_Mk2.chat.model.ChatMessage;
import com.starkIndustries.webSocket_Mk2.chat.repository.ChatMessageRepository;
import com.starkIndustries.webSocket_Mk2.chatRoom.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ChatMessageService {

    @Autowired
    public ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatRoomService chatRoomService;

    public ChatMessage saveChatMessage(ChatMessage chatMessage){

        if(chatMessage!=null){
            String chatRoomId = this.chatRoomService.getChatRoomId(
                    chatMessage.getSenderId(),
                    chatMessage.getReceiverId(),
                    true
            );
        }

    }


}
