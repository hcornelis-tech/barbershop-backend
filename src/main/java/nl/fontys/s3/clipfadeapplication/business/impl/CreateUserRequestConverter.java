package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;

final class CreateUserRequestConverter {
    private CreateUserRequestConverter() {}

    public static CreateUserRequest convert(CreateBarberRequest request) {
        return CreateUserRequest.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .password(request.getPassword())
                .confirm_password(request.getConfirm_password())
                .is_barber(request.is_barber())
                .date_time(request.getDate_time())
                .build();
    }
}
