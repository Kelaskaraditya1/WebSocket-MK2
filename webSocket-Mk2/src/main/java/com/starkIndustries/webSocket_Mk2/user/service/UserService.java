package com.starkIndustries.webSocket_Mk2.user.service;

import com.starkIndustries.webSocket_Mk2.keys.Keys;
import com.starkIndustries.webSocket_Mk2.user.dto.request.UserRequest;
import com.starkIndustries.webSocket_Mk2.user.enums.Status;
import com.starkIndustries.webSocket_Mk2.user.model.User;
import com.starkIndustries.webSocket_Mk2.user.repository.UserRepository;
import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import com.starkIndustries.webSocket_Mk2.webSocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public Message addUser(UserRequest userRequest){

        SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create();

        if(userRequest!=null){

            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .name(userRequest.getName())
                    .username(userRequest.getUsername())
                    .status(Status.ONLINE)
                    .build();
            this.userRepository.save(user);

            simpMessageHeaderAccessor.getSessionAttributes().put(Keys.USERNAME,userRequest.getUsername());
            Message message = Message.builder()
                    .name(userRequest.getName())
                    .message(userRequest.getName()+" Joined the chat!!")
                    .messageType(MessageType.JOIN)
                    .build();

            return message;
        }
        return null;
    }

    public Message disconnectUser(String username){

        User user= this.userRepository.findAll()
                .stream()
                .filter(user1->user1.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if(user!=null){

            user.setStatus(Status.OFFLINE);
            this.userRepository.save(user);
            return Message.builder()
                    .name(username)
                    .message(username+" left the chat!!")
                    .messageType(MessageType.LEAVE)
                    .build();
        }
        return null;
    }

    public List<User> getAllOnlineUsers(){

        List<User> onlineUsers = this.userRepository.findAll()
                .stream()
                .filter(user->user.getStatus().equals(Status.ONLINE))
                .collect(Collectors.toList());

        if(!onlineUsers.isEmpty())
            return onlineUsers;

        return new ArrayList<User>(Collections.emptyList());
    }

}
