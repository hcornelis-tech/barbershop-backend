package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.AvailableTimeSlot;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AvailableTimeSlotEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AvailableTimeSlotConverterTest {
    @Mock
    private AvailableTimeSlotEntity availableTimeSlotEntity;

    @Test
    void convert() {
        //Arrange
        AvailableTimeSlotEntity timeSlotEntity = AvailableTimeSlotEntity.builder()
                .date("2025-01-16")
                .timeslots(Collections.emptyList())
                .build();

        //Act
        AvailableTimeSlot availableTimeSlot = AvailableTimeSlotConverter.convert(timeSlotEntity);

        //Assert
        assertEquals(timeSlotEntity.getDate(), availableTimeSlot.getDate());
    }
}
