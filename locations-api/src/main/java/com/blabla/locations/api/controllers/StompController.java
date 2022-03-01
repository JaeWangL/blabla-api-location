package com.blabla.locations.api.controllers;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {
    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/location/update")
    public void enter(UpdateLocationRequest message){
        // TODO
    }
}
