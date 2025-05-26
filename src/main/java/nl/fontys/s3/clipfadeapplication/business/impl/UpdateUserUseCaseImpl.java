package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateUserUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserRequest request) {
        UserEntity user = getUser(request.getId()) ;

        updateUserValues(user, request);
    }

    public UserEntity getUser(int id) {
        Optional<UserEntity> customerOptional = userRepository.findById(Long.valueOf(id));
        if (customerOptional.isEmpty()) {
            throw new InvalidUserException("User with id " + id + " not found");
        }

        return customerOptional.get();
    }

    public void updateUserValues(UserEntity user, UpdateUserRequest request) {
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setEmail(request.getEmail());
        user.setPhone_number(request.getPhone_number());

        userRepository.save(user);
    }
}
