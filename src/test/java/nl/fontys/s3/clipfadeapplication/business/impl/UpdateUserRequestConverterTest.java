package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateUserRequestConverterTest {
    @Test
    void convert() {
        UpdateBarberRequest barberRequest = UpdateBarberRequest.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();

        UpdateUserRequest userRequest = UpdateUserRequestConverter.convert(barberRequest);

        assertEquals(barberRequest.getFirst_name(), userRequest.getFirst_name());
        assertEquals(barberRequest.getLast_name(), userRequest.getLast_name());
        assertEquals(barberRequest.getEmail(), userRequest.getEmail());
        assertEquals(barberRequest.getPhone_number(), userRequest.getPhone_number());
    }
}
