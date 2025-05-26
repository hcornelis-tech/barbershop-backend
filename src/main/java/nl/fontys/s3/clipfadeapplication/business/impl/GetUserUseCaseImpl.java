package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetUserUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.response.GetUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;

    @Override
    public GetUserResponse getUser(int id) {
        Optional<UserEntity> customerOptional = userRepository.findById(Long.valueOf(id));
        if (customerOptional.isEmpty()) {
            throw new InvalidUserException("Customer not found");
        }

        UserEntity customer = customerOptional.get();

        return UserConverter.convert(customer);
    }
}
