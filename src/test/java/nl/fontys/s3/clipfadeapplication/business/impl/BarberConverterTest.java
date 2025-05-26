package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BarberConverterTest {
    @Mock
    private BarberEntity barberEntity;

    @Test
    void convert() {
        UserEntity user = UserEntity.builder()
                .first_name("Boblee")
                .last_name("Swagger")
                .email("swagger@hotmail.com")
                .phone_number("123")
                .password("123")
                .date_time("2024-11-04")
                .is_barber(true)
                .appointments(Collections.emptyList())
                .build();

        when(barberEntity.getId()).thenReturn(1);
        when(barberEntity.getUser()).thenReturn(user);
        when(barberEntity.getHire_date()).thenReturn("2024-11-04");
        when(barberEntity.is_admin()).thenReturn(false);

        GetBarberResponse result = BarberConverter.convert(barberEntity);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Boblee", result.getFirst_name());
        assertEquals("Swagger", result.getLast_name());
        assertEquals("swagger@hotmail.com", result.getEmail());
        assertEquals("2024-11-04", result.getHire_date());
        assertFalse(result.is_admin());
    }
}
