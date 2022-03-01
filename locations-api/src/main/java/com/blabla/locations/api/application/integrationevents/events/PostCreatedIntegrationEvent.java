package com.blabla.locations.api.application.integrationevents.events;

import com.blabla.locations.common.eventhandling.IntegrationEvent;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostCreatedIntegrationEvent extends IntegrationEvent {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
