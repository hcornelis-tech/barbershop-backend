package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.LoginUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidCredentialsException;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidUserException;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.clipfadeapplication.domain.request.LoginRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.LoginResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final BarberRepository barberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new InvalidUserException("User not found");
        }

        if (!matchesPassword(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect email or password");
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().access_token(accessToken).build();
    }

    public String generateAccessToken(UserEntity user) {
        int userId = user.getId();
        Set<String> roles = new HashSet<>();

        if (user.is_barber()) {
            roles.add("BARBER");

            BarberEntity barber = getBarber(userId);

            if (barber.is_admin()) {
                roles.add("ADMIN");
            }
        }
        else {
            roles.add("CUSTOMER");
        }

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), userId, roles));
    }

    private boolean matchesPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    private BarberEntity getBarber(int userId) {
        Optional<BarberEntity> barberEntity = barberRepository.findByUserId(userId);
        if (barberEntity.isEmpty()) {
            throw new InvalidBarberException("Barber not found");
        }

        return barberEntity.get();
    }
}
