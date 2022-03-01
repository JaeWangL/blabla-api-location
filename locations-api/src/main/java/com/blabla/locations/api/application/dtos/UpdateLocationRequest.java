package com.blabla.locations.api.application.dtos;

import java.math.BigDecimal;

public record UpdateLocationRequest(
    Short deviceType,
    String deviceId,
    BigDecimal latitude,
    BigDecimal longitude
) {
}
