package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetAvailableTimeSlotsUseCase;
import nl.fontys.s3.clipfadeapplication.domain.AvailableTimeSlot;
import nl.fontys.s3.clipfadeapplication.domain.response.GetAvailableTimeSlotsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.ScheduleRepository;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAvailableTimeSlotsUseCaseImpl implements GetAvailableTimeSlotsUseCase {
    private List<AvailableTimeSlotEntity> availabilities;
    private AppointmentRepository appointmentRepository;
    private List<AppointmentEntity> barbersAppointments;
    private ScheduleRepository scheduleRepository;
    private TimeBlockerRepository timeBlockerRepository;

    @Override
    public GetAvailableTimeSlotsResponse availableTimeSlots(int barberId, int serviceDuration) {
        availabilities = new ArrayList<>();
        barbersAppointments = appointmentRepository.findByBarber(Long.valueOf(barberId));
        ScheduleEntity schedule = getSchedule();

        String startTime = schedule.getStart_time();
        String endTime = schedule.getEnd_time();
        int minutes = schedule.getService_duration();

        LocalDate startDate = LocalDate.now();

        if (availabilities.isEmpty() || !availabilities.get(0).getDate().equals(startDate.toString())) {
            availabilities.clear();
            for (int i = 1; i < 120; i++) {
                LocalDate dateToAdd = startDate.plusDays(i);
                DayOfWeek dayOfWeek = dateToAdd.getDayOfWeek();

                boolean isCompanyOpen =
                        (dayOfWeek == DayOfWeek.MONDAY && schedule.isMonday()) ||
                                (dayOfWeek == DayOfWeek.TUESDAY && schedule.isTuesday()) ||
                                (dayOfWeek == DayOfWeek.WEDNESDAY && schedule.isWednesday()) ||
                                (dayOfWeek == DayOfWeek.THURSDAY && schedule.isThursday()) ||
                                (dayOfWeek == DayOfWeek.FRIDAY && schedule.isFriday()) ||
                                (dayOfWeek == DayOfWeek.SATURDAY && schedule.isSaturday()) ||
                                (dayOfWeek == DayOfWeek.SUNDAY && schedule.isSunday());

                List<TimeSlotEntity> timeSlotsForDay = Collections.emptyList();

                TimeBlockerEntity timeBlock = timeBlockerRepository.checkIfBlockedBetweenDates(dateToAdd.toString(), Long.valueOf(barberId));

                if (isCompanyOpen) {
                    timeSlotsForDay = generateTimeSlots(startTime, endTime, minutes, dateToAdd, serviceDuration, timeBlock);
                }

                availabilities.add(AvailableTimeSlotEntity.builder()
                        .date(dateToAdd.toString())
                        .timeslots(timeSlotsForDay)
                        .build());
            }
        }

        final GetAvailableTimeSlotsResponse response = new GetAvailableTimeSlotsResponse();
        List<AvailableTimeSlot> availableTimeSlot = availabilities
                .stream()
                .map(AvailableTimeSlotConverter::convert)
                .toList();

        response.setAvailabilities(availableTimeSlot);

        return response;
    }

    private List<TimeSlotEntity> generateTimeSlots(String startTime, String endTime, int minutes, LocalDate date, int serviceDuration, TimeBlockerEntity timeBlock) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        List<LocalTime[]> appointmentTimes = getAppointmentTimes(date);
        List<TimeSlotEntity> timeSlotsForDay = new ArrayList<>();

        LocalDate blockedStartDate = null;
        LocalDate blockedEndDate = null;
        LocalTime blockedStart = null;
        LocalTime blockedEnd = null;

        if (timeBlock != null) {
            blockedStartDate = LocalDate.parse(timeBlock.getStart_date());
            blockedEndDate = LocalDate.parse(timeBlock.getEnd_date());

            if (date.equals(blockedStartDate)) {
                blockedStart = LocalTime.parse(timeBlock.getStart_time());
            }
            else {
                blockedStart = LocalTime.parse(getSchedule().getStart_time());
            }

            if (date.equals(blockedEndDate)) {
                blockedEnd = LocalTime.parse(timeBlock.getEnd_time());
            }
            else {
                blockedEnd = LocalTime.parse(getSchedule().getEnd_time());
            }
        }

        int totalSlots = (int) (start.until(end, ChronoUnit.MINUTES) / minutes);

        for (int j = 0; j < totalSlots; j++) {
            LocalTime endOfService = start.plusMinutes(serviceDuration);
            boolean timeSlotAvailable = true;

            for (LocalTime[] appointmentTime : appointmentTimes) {
                LocalTime appointmentStart = appointmentTime[0];
                LocalTime appointmentEnd = appointmentTime[1];

                if ((start.isBefore(appointmentEnd) && endOfService.isAfter(appointmentStart)) ||
                        (appointmentStart.isBefore(endOfService) && appointmentEnd.isAfter(start))) {
                    timeSlotAvailable = false;
                    break;
                }
            }

            if (timeBlock != null) {
                if ((start.isBefore(blockedEnd.plusMinutes(1)) && endOfService.isAfter(blockedStart)) ||
                        (blockedStart.isBefore(endOfService) && blockedEnd.plusMinutes(1).isAfter(start))) {
                    timeSlotAvailable = false;
                }

                if (date.isAfter(LocalDate.parse(timeBlock.getStart_date())) && date.isBefore(LocalDate.parse(timeBlock.getEnd_date()))) {
                    return Collections.emptyList();
                }
            }

            if (timeSlotAvailable && (endOfService.isBefore(end) || endOfService.equals(end))) {
                timeSlotsForDay.add(TimeSlotEntity.builder()
                        .time(start.toString())
                        .build());
            }

            start = start.plusMinutes(minutes);
        }

        return timeSlotsForDay;
    }

    private List<LocalTime[]> getAppointmentTimes(LocalDate date) {
        return barbersAppointments.stream()
                .filter(appointment -> LocalDate.parse(appointment.getDate()).isEqual(date) &&
                        !appointment.getStatus().equalsIgnoreCase("Cancelled"))
                .map(appointment -> {
                    LocalTime appointmentStart = LocalTime.parse(appointment.getTime());
                    LocalTime appointmentEnd = appointmentStart.plusMinutes(appointment.getService().getDuration());
                    return new LocalTime[]{appointmentStart, appointmentEnd};
                })
                .toList();
    }

    private ScheduleEntity getSchedule() {
        Optional<ScheduleEntity> schedule = scheduleRepository.findById(Long.valueOf(1));
        if (schedule.isEmpty()) {
            throw new RuntimeException("Schedule not found!");
        }

        return schedule.get();
    }
}