package com.starkIndustries.webSocket_Mk2.webSocket.eventListner;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    public void userDisConnect(SessionDisconnectEvent sessionDisconnectEvent){



    }
}
