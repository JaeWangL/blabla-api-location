package com.blabla.locations.api.infrastructure.services;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.application.models.UserSessionInfo;
import com.blabla.locations.api.infrastructure.entities.UserEntity;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserEntity create(String sessionId, UpdateLocationRequest input);

    UserEntity updateLocation(UserEntity entity, String sessionId, UpdateLocationRequest input);

    UserEntity updateSessionId(UserEntity entity, String sessionId);

    void resetSessionId(String sessionId);

    void upsert(String sessionId, UpdateLocationRequest input);

    void deleteBySessionId(String sessionId);

    List<UserSessionInfo> getUsersByDistance(BigDecimal currentLatitude, BigDecimal currentLongitude, double distanceInKm, Integer pageSize, Integer pageIndex);
}
