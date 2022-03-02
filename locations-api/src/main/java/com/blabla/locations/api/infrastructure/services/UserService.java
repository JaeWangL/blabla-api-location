package com.blabla.locations.api.infrastructure.services;

import com.blabla.locations.api.application.dtos.UpdateLocationRequest;
import com.blabla.locations.api.infrastructure.entities.UserEntity;

public interface UserService {
    UserEntity create(UpdateLocationRequest input);

    UserEntity updateLocation(UserEntity entity, UpdateLocationRequest input);

    void upsert(UpdateLocationRequest input);
}

