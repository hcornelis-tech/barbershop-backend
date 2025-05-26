package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
    UserEntity saveNewUser(CreateUserRequest request);
}
