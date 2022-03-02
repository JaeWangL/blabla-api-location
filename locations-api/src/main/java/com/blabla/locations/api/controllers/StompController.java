package com.blabla.locations.api.controllers;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.infrastructure.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {
    private final SimpMessagingTemplate template;
    private final UserService userSvc;

    @MessageMapping(value = "/location/update")
    public void updateLocation(UpdateLocationRequest message){
        userSvc.upsert(message);
    }
}
