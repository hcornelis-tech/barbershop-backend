package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateUserUseCase;
import nl.fontys.s3.clipfadeapplication.business.LoginUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.*;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateUserResponse;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final GenerateRandomId generateRandomId;
    private final UserRepository userRepository;
    private final LoginUseCase loginUseCase;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String PHONE_NUMBER_REGEX = "^[+]?[-0-9]+$";

    public CreateUserResponse createUser(CreateUserRequest request) {
        UserEntity user = saveNewUser(request);

        String accessToken = loginUseCase.generateAccessToken(user);

        return CreateUserResponse.builder()
                .id(user.getId())
                .access_token(accessToken)
                .build();
    }

    public UserEntity saveNewUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExists();
        }

        if (!isValidEmail(request.getEmail())) {
            throw new InvalidEmailAddress("Invalid email");
        }

        if (!isValidPhoneNumber(request.getPhone_number())) {
            throw new InvalidPhoneNumber("Invalid phone number");
        }

        if (!request.getPassword().equals(request.getConfirm_password())) {
            throw new InvalidPassword("Your passwords doesn't match");
        }

        int randomId = generateUniqueRandomId();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newCustomer = UserEntity.builder()
                .id(randomId)
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .password(hashedPassword)
                .date_time(request.getDate_time())
                .is_barber(request.is_barber())
                .appointments(Collections.emptyList())
                .build();

        return userRepository.save(newCustomer);
    }

    private int generateUniqueRandomId() {
        int randomId;

        do {
            randomId = generateRandomId.generate(10000000, 90000000);
        } while (userRepository.existsById(randomId));

        return randomId;
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
