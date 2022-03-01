package com.blabla.locations.api.application.integrationevents.eventhandling;

import com.blabla.locations.api.application.integrationevents.events.PostCreatedIntegrationEvent;
import com.blabla.locations.common.eventhandling.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostCreatedIntegrationEventHandler implements
    IntegrationEventHandler<PostCreatedIntegrationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PostCreatedIntegrationEventHandler.class);

    @KafkaListener(
        groupId = "${app.kafka.group.createdPosts}",
        topics = "${spring.kafka.consumer.topic.createdPosts}"
    )
    @Override
    public void handle(PostCreatedIntegrationEvent event) {
        logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    }
}
