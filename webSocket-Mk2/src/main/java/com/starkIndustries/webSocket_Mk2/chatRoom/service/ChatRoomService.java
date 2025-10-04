package com.starkIndustries.webSocket_Mk2.chatRoom.service;

import com.starkIndustries.webSocket_Mk2.chatRoom.model.ChatRoom;
import com.starkIndustries.webSocket_Mk2.chatRoom.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    public ChatRoomRepository chatRoomRepository;

    public Optional<String> createChatRoomId(
            String senderId,
            String receiverId
    ){
        var chatRoomId = senderId+"_"+receiverId;

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom chatRoom1 = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();

        this.chatRoomRepository.save(chatRoom1);
        this.chatRoomRepository.save(chatRoom);

        return Optional.of(chatRoomId);

    }


    public Optional<String> getChatRoomId(
            String senderId,
            String receiverId,
            boolean createIfNotExist
    ) {
        return this.chatRoomRepository.findBySenderIdAndReceiverId(senderId,receiverId)
                .map(chatRoom -> chatRoom.getId())
                .or(()->{
                    if(createIfNotExist)
                        return Optional.of(createChatRoomId(senderId,receiverId).get());
                    return Optional.empty();
                });

        }

}
