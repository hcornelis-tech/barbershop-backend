package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;

final class UserConverter {
    private UserConverter() {}

    public static GetUserResponse convert(UserEntity user){
        return GetUserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .phone_number(user.getPhone_number())
                .date_time(user.getDate_time())
                .is_barber(user.is_barber())
                .build();
    }
}
