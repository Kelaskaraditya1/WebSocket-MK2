package com.starkIndustries.webSocket_Mk2.webSocket.eventListner;

import com.starkIndustries.webSocket_Mk2.keys.Keys;
import com.starkIndustries.webSocket_Mk2.webSocket.enums.MessageType;
import com.starkIndustries.webSocket_Mk2.webSocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    public SimpMessageSendingOperations simpMessageSendingOperations;

    public void userDisConnect(SessionDisconnectEvent sessionDisconnectEvent){

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String username = stompHeaderAccessor.getSessionAttributes().get(Keys.USERNAME).toString();

        if(username!=null){
            Message message = Message.builder()
                    .name(username)
                    .message(username+" Left the chat!!")
                    .messageType(MessageType.LEAVE)
                    .build();

            log.info("User Disconnected : "+username);

            simpMessageSendingOperations.convertAndSend("/topic/public/messages",message);
        }


    }
}
