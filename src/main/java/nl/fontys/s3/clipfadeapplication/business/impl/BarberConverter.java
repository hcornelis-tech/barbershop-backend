package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Barber;
import nl.fontys.s3.clipfadeapplication.domain.response.GetBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;

final class BarberConverter {
    private BarberConverter() {}

    public static GetBarberResponse convert(BarberEntity barber) {
        return  GetBarberResponse.builder()
                .id(barber.getId())
                .first_name(barber.getUser().getFirst_name())
                .last_name(barber.getUser().getLast_name())
                .email(barber.getUser().getEmail())
                .phone_number(barber.getUser().getPhone_number())
                .date_time(barber.getUser().getDate_time())
                .is_admin(barber.is_admin())
                .hire_date(barber.getHire_date())
                //.availabilities(barber.getAvailabilities().stream().map(AvailabilityConverter::convert).toList())
                .build();
    }
}
