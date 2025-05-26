package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.LoginRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.LoginResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);

    String generateAccessToken(UserEntity user);
}
