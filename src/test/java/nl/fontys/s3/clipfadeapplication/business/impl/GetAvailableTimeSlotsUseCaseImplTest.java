package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.AvailableTimeSlot;
import nl.fontys.s3.clipfadeapplication.domain.response.GetAvailableTimeSlotsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AvailableTimeSlotEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.ScheduleEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.TimeBlockerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAvailableTimeSlotsUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TimeBlockerRepository timeBlockerRepository;

    @InjectMocks
    private GetAvailableTimeSlotsUseCaseImpl getAvailableTimeSlotsUseCase;

    private List<AppointmentEntity> barbersAppointments;
    private ScheduleEntity schedule;
    private List<AvailableTimeSlotEntity> availabilities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        barbersAppointments = new ArrayList<>();
        availabilities = new ArrayList<>();
        schedule = new ScheduleEntity();
        schedule.setStart_time("09:00");
        schedule.setEnd_time("18:00");
        schedule.setService_duration(30);
        schedule.setMonday(true);
        schedule.setTuesday(true);
        schedule.setWednesday(true);
        schedule.setThursday(true);
        schedule.setFriday(true);
        schedule.setSaturday(false);
        schedule.setSunday(false);
    }

    @Test
    void getAvailableTimeSlot_shouldReturnAvailableTimeSlots() {
        //Arrange
        int barberId = 1;
        int serviceDuration = 30;

        when(appointmentRepository.findByBarber(anyLong())).thenReturn(barbersAppointments);
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(timeBlockerRepository.checkIfBlockedBetweenDates(anyString(), anyLong())).thenReturn(null);

        //Act
        GetAvailableTimeSlotsResponse response = getAvailableTimeSlotsUseCase.availableTimeSlots(barberId, serviceDuration);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getAvailabilities());
    }

    @Test
    void getAvailableTimeSlot_shouldReturnAvailableTimeSlotsWhenNoAppointments() {
        //Arrange
        int barberId = 1;
        int serviceDuration = 30;

        when(appointmentRepository.findByBarber(anyLong())).thenReturn(Collections.emptyList());
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(timeBlockerRepository.checkIfBlockedBetweenDates(anyString(), anyLong())).thenReturn(null);

        // Act
        GetAvailableTimeSlotsResponse response = getAvailableTimeSlotsUseCase.availableTimeSlots(barberId, serviceDuration);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getAvailabilities());

        AvailableTimeSlot availableTimeSlot = response.getAvailabilities().get(0);
        assertNotNull(availableTimeSlot.getDate());
        assertNotNull(availableTimeSlot.getTimeslots());
    }

    @Test
    void getAvailableTimeSlot_shouldReturnAvailableTimeSlotsWithBlockedTimes() {
        //Arrange
        int barberId = 1;
        int serviceDuration = 30;

        TimeBlockerEntity timeBlockerEntity = new TimeBlockerEntity();
        timeBlockerEntity.setStart_date("2025-01-20");
        timeBlockerEntity.setEnd_date("2025-01-20");
        timeBlockerEntity.setStart_time("09:00");
        timeBlockerEntity.setEnd_time("12:00");

        when(appointmentRepository.findByBarber(anyLong())).thenReturn(barbersAppointments);
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(timeBlockerRepository.checkIfBlockedBetweenDates(anyString(), anyLong())).thenReturn(timeBlockerEntity);

        //Act
        GetAvailableTimeSlotsResponse response = getAvailableTimeSlotsUseCase.availableTimeSlots(barberId, serviceDuration);

        assertNotNull(response);
        assertNotNull(response.getAvailabilities());
        assertFalse(response.getAvailabilities().isEmpty());

        AvailableTimeSlot availableTimeSlot = response.getAvailabilities().get(0);
        assertNotNull(availableTimeSlot.getDate());
        assertNotNull(availableTimeSlot.getTimeslots());
    }
}
