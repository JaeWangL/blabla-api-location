package com.blabla.locations.api.infrastructure.repositories;

import com.blabla.locations.api.infrastructure.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByDeviceDeviceTypeAndDeviceDeviceId(Short deviceType, String deviceId);
}
