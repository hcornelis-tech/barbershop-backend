package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserConverterTest {
    @Test
    void convert() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .first_name("Jane")
                .last_name("Doe")
                .email("janedoe@gmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(false)
                .appointments(Collections.emptyList())
                .build();

        GetUserResponse user = UserConverter.convert(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getFirst_name(), user.getFirst_name());
        assertEquals(userEntity.getLast_name(), user.getLast_name());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getPhone_number(), user.getPhone_number());
        assertEquals(userEntity.getDate_time(), user.getDate_time());
    }
}
