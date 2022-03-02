package com.blabla.locations.api.application.integrationevents.eventhandling;

import com.blabla.locations.api.application.integrationevents.events.PostCreatedIntegrationEvent;
import com.blabla.locations.api.infrastructure.services.UserService;
import com.blabla.locations.common.eventhandling.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostCreatedIntegrationEventHandler implements
    IntegrationEventHandler<PostCreatedIntegrationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PostCreatedIntegrationEventHandler.class);
    private static final String DESTINATION = "/queue/post-created";
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userSvc;

    @KafkaListener(
        groupId = "${app.kafka.group.createdPosts}",
        topics = "${spring.kafka.consumer.topic.createdPosts}"
    )
    @Override
    public void handle(PostCreatedIntegrationEvent event) {
        logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

        final var usersInDistance = userSvc.getUsersByDistance(event.getLatitude(), event.getLongitude(), 1.0, 10, 0);

        // TODO: Optimize this, ex) using broadcast all users in near area
        usersInDistance.forEach((user) -> {
            messagingTemplate.convertAndSendToUser(user.sessionId(), "/queue/post/new",
                event,
                createHeaders(user.sessionId()));
        });
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
            .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
