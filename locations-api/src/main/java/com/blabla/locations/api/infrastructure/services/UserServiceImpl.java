package com.blabla.locations.api.infrastructure.services;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.infrastructure.entities.DeviceInfoModel;
import com.blabla.locations.api.infrastructure.entities.LocationModel;
import com.blabla.locations.api.infrastructure.entities.UserEntity;
import com.blabla.locations.api.infrastructure.repositories.UserEntityRepository;
import com.github.ksuid.Ksuid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userRepo;

    public UserEntity create(UpdateLocationRequest input) {
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
            .build();
        userRepo.save(newEntity);

        return newEntity;
    }

    public UserEntity updateLocation(UserEntity entity, UpdateLocationRequest input) {
        entity.setLocation(LocationModel.builder()
            .latitude(input.latitude())
            .longitude(input.longitude())
            .build());
        userRepo.save(entity);

        return entity;
    }

    public void upsert(UpdateLocationRequest input) {
        userRepo.findByDeviceDeviceTypeAndDeviceDeviceId(input.deviceType(), input.deviceId())
            .ifPresentOrElse(
                (value) -> updateLocation(value, input),
                () -> create(input));
    }
}
