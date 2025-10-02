package com.starkIndustries.webSocket_Mk2.user.controller;

import com.starkIndustries.webSocket_Mk2.user.dto.request.UserRequest;
import com.starkIndustries.webSocket_Mk2.user.service.UserService;
import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import com.starkIndustries.webSocket_Mk2.webSocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @MessageMapping("/add/user")
    @SendTo("/topic/public/messages")
    public Message addUser(
            @Payload UserRequest userRequest
            ){

        Message message = this.userService.addUser(userRequest);
        if(message!=null)
            return message;

        return Message.builder()
                .message("Error Occurred")
                .messageType(MessageType.ERROR)
                .build();
    }

    @MessageMapping("/disconnect/user")
    @SendTo("/topic/public/messages")
    public Message dissConnectUser(String username){
        Message message = this.userService.disconnectUser(username);
        if(message!=null)
            return message;

        return Message.builder()
                .message("Error Occurred")
                .messageType(MessageType.ERROR)
                .build();
    }

}
