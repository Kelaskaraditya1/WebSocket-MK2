package com.starkIndustries.webSocket_Mk2.user.controller;

import com.starkIndustries.webSocket_Mk2.keys.Keys;
import com.starkIndustries.webSocket_Mk2.user.dto.request.UserRequest;
import com.starkIndustries.webSocket_Mk2.user.model.User;
import com.starkIndustries.webSocket_Mk2.user.service.UserService;
import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import com.starkIndustries.webSocket_Mk2.webSocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @MessageMapping("/add/user")
    @SendTo("/topic/public/messages")
    public Message addUser(
            @Payload UserRequest userRequest
    ) {

        Message message = this.userService.addUser(userRequest);
        if (message != null) {
            log.info("User added: {}", userRequest.getUsername());
            return message;
        }


        return Message.builder()
                .message("Error Occurred")
                .messageType(MessageType.ERROR)
                .build();
    }

    @MessageMapping("/disconnect/user")
    @SendTo("/topic/public/messages")
    public Message dissConnectUser(String username) {
        Message message = this.userService.disconnectUser(username);
        if (message != null)
            return message;

        return Message.builder()
                .message("Error Occurred")
                .messageType(MessageType.ERROR)
                .build();
    }

    @GetMapping("/get/online/users")
    public ResponseEntity<?> getAllOnlineUsers() {

        Map<String, Object> response = new HashMap<>();
        response.put(Keys.TIME_STAMP, Instant.now());

        List<User> users = this.userService.getAllOnlineUsers();

        if (!users.isEmpty()) {
            response.put(Keys.STATUS, "Success");
            response.put(Keys.STATUS_CODE, 200);
            response.put(Keys.MESSAGE, "Online users retrieved successfully");
            response.put(Keys.DATA, users);
            return ResponseEntity.status(200).body(response);
        }
        response.put(Keys.STATUS, HttpStatus.OK);
        response.put(Keys.STATUS_CODE,HttpStatus.OK.value());
        response.put(Keys.MESSAGE, "No online users found");
        response.put(Keys.DATA, users);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}