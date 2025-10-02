package com.starkIndustries.webSocket_Mk2.chat.repository;

import com.starkIndustries.webSocket_Mk2.chat.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
}
