package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserRequestConverterTest {
    @Test
    void convert() {
        CreateBarberRequest barberRequest = CreateBarberRequest.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(true)
                .build();

        CreateUserRequest userRequest = CreateUserRequestConverter.convert(barberRequest);

        assertEquals(barberRequest.getFirst_name(), userRequest.getFirst_name());
        assertEquals(barberRequest.getLast_name(), userRequest.getLast_name());
        assertEquals(barberRequest.getEmail(), userRequest.getEmail());
    }
}
