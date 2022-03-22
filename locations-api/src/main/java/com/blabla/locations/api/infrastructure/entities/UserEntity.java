package com.blabla.locations.api.infrastructure.entities;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "session_id", nullable = false, columnDefinition = "varchar(255) default ''")
    private String sessionId;

    @Embedded
    private DeviceInfoModel device;

    @Embedded
    private LocationModel location;
}
