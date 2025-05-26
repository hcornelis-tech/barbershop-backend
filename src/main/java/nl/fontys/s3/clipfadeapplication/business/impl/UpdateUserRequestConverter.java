package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;

final class UpdateUserRequestConverter {
    private UpdateUserRequestConverter() {}

    public static UpdateUserRequest convert(UpdateBarberRequest request) {
        return UpdateUserRequest.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .build();
    }
}
