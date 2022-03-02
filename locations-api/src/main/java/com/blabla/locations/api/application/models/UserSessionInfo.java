package com.blabla.locations.api.application.models;

import lombok.NonNull;

public record UserSessionInfo(
    @NonNull String sessionId,
    @NonNull Short deviceType,
    @NonNull String deviceId
) {
    public static UserSessionInfo fromParameters(
        String sessionId,
        @NonNull Short deviceType,
        @NonNull String deviceId
    ) {
        return new UserSessionInfo(
            sessionId,
            deviceType,
            deviceId
        );
    }
}
