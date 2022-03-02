package com.blabla.locations.api.infrastructure.services;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.application.models.UserSessionInfo;
import com.blabla.locations.api.infrastructure.entities.DeviceInfoModel;
import com.blabla.locations.api.infrastructure.entities.LocationModel;
import com.blabla.locations.api.infrastructure.entities.UserEntity;
import com.blabla.locations.api.infrastructure.repositories.UserEntityRepository;
import com.github.ksuid.Ksuid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userRepo;
    private final EntityManager entityManager;

    @Override
    public UserEntity create(String sessionId, UpdateLocationRequest input) {
        final var newEntity = UserEntity.builder()
            .id(Ksuid.newKsuid().toString())
            .device(DeviceInfoModel.builder()
                .deviceType(input.deviceType())
                .deviceId(input.deviceId())
                .build())
            .location(LocationModel.builder()
                .latitude(input.latitude())
                .longitude(input.longitude())
                .build())
            .sessionId(sessionId)
            .build();
        userRepo.save(newEntity);

        return newEntity;
    }

    @Override
    public UserEntity updateLocation(UserEntity entity, String sessionId, UpdateLocationRequest input) {
        entity.setLocation(LocationModel.builder()
            .latitude(input.latitude())
            .longitude(input.longitude())
            .build());
        entity.setSessionId(sessionId);
        userRepo.save(entity);

        return entity;
    }

    @Override
    public UserEntity updateSessionId(UserEntity entity, String sessionId) {
        entity.setSessionId(sessionId);
        userRepo.save(entity);

        return entity;
    }

    @Override
    public void resetSessionId(String sessionId) {
        userRepo.findBySessionId(sessionId)
            .ifPresent((value) -> updateSessionId(value, ""));
    }

    @Override
    public void upsert(String sessionId, UpdateLocationRequest input) {
        userRepo.findByDeviceDeviceTypeAndDeviceDeviceId(input.deviceType(), input.deviceId())
            .ifPresentOrElse(
                (value) -> updateLocation(value, sessionId, input),
                () -> create(sessionId, input));
    }

    @Override
    public void deleteBySessionId(String sessionId) {
        userRepo.deleteBySessionId(sessionId);
    }

    @Override
    public List<UserSessionInfo> getUsersByDistance(BigDecimal currentLatitude, BigDecimal currentLongitude, double distanceInKm, Integer pageSize, Integer pageIndex) {
        var query = entityManager.createNativeQuery("""
        SELECT id, device_type, device_id, session_id, distance
        FROM (
            SELECT z.id,
                   z.device_type, z.device_id,
                   z.session_id,
                   p.radius,
                   p.distance_unit
                       * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))
                       * COS(RADIANS(z.latitude))
                       * COS(RADIANS(p.longpoint - z.longitude))
                       + SIN(RADIANS(p.latpoint))
                       * SIN(RADIANS(z.latitude))))) AS distance
            FROM users AS z
            JOIN (
                SELECT :currentLatitude AS latpoint, :currentLongitude AS longpoint,
                       :distanceInKm AS radius, 111.045 AS distance_unit
            ) AS p ON 1=1
            WHERE z.latitude
                BETWEEN p.latpoint  - (p.radius / p.distance_unit)
                    AND p.latpoint  + (p.radius / p.distance_unit)
            AND z.longitude
                BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))
                    AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))
        ) AS d
        WHERE distance <= radius
        ORDER BY distance
        LIMIT :pageSize OFFSET :pageIndex
        """)
            .setParameter("currentLatitude", currentLatitude)
            .setParameter("currentLongitude", currentLongitude)
            .setParameter("distanceInKm", distanceInKm)
            .setParameter("pageSize", pageSize)
            .setParameter("pageIndex", pageIndex);
        List<Object[]> results = query.getResultList();

        return results.stream()
            .map(r -> {
                var id = (String) r[0];
                var deviceType = (Short) r[1];
                var deviceId = (String) r[2];
                var sessionId = (String) r[3];

                return UserSessionInfo.fromParameters(sessionId, deviceType, deviceId);
            }).collect(Collectors.toList());
    }
}
