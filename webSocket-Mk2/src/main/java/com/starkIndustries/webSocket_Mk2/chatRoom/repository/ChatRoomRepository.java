package com.starkIndustries.webSocket_Mk2.chatRoom.repository;

import com.starkIndustries.webSocket_Mk2.chatRoom.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {

    public Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);

}
