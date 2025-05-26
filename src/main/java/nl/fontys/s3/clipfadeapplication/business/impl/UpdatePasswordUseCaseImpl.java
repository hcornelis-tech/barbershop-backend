package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdatePasswordUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidPassword;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdatePasswordRequest;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(Long.valueOf(request.getId()));
        if (userOptional.isEmpty()) {
            throw new InvalidUserException("User with id " + request.getId() + " not found");
        }

        UserEntity user = userOptional.get();

        if (!matchesPassword(request.getCurrent_password(), user.getPassword())) {
            throw new InvalidPassword("Current password was incorrect");
        }

        if (!request.getNew_password().equals(request.getConfirm_new_password())) {
            throw new InvalidPassword("Your passwords doesn't match");
        }

        String hashedPassword = passwordEncoder.encode(request.getNew_password());

        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    private boolean matchesPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
