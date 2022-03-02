package com.blabla.locations.api.controllers;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.infrastructure.services.UserService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@RequiredArgsConstructor
public class StompController {
    private final UserService userSvc;

    @MessageMapping(value = "/location/update")
    public void updateLocation(StompHeaderAccessor headerAccessor, UpdateLocationRequest message) {
        userSvc.upsert(headerAccessor.getSessionId(), message);
    }

    @EventListener
    @Transactional
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        userSvc.deleteBySessionId(event.getSessionId());
    }
}
