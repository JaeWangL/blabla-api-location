package com.blabla.locations.api.infrastructure.entities;

import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
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
    @Embedded
    private DeviceInfoModel device;

    @Embedded
    private LocationModel location;
}
