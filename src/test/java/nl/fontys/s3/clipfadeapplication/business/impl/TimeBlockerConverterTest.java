package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetTimeBlockerResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeBlockerConverterTest {

    @Mock
    private TimeBlockerEntity timeBlockerEntity;

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

        BarberEntity barber = BarberEntity.builder()
                .user(user)
                .hire_date("2024-11-04")
                .is_admin(true)
                .build();

        when(timeBlockerEntity.getId()).thenReturn(1);
        when(timeBlockerEntity.getStart_date()).thenReturn("2024-11-06");
        when(timeBlockerEntity.getEnd_date()).thenReturn("2024-11-07");
        when(timeBlockerEntity.getStart_time()).thenReturn("10:00");
        when(timeBlockerEntity.getEnd_time()).thenReturn("16:00");
        when(timeBlockerEntity.getBarber()).thenReturn(barber);

        GetTimeBlockerResponse result = TimeBlockerConverter.convert(timeBlockerEntity);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("2024-11-06", result.getStart_date());
        assertEquals("2024-11-07", result.getEnd_date());
        assertEquals("10:00", result.getStart_time());
        assertEquals("16:00", result.getEnd_time());
    }
}
