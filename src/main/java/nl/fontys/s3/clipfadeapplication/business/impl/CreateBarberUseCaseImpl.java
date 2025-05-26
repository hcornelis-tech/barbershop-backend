package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreateBarberUseCase;
import nl.fontys.s3.clipfadeapplication.business.CreateUserUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateUserRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateBarberResponse;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateBarberUseCaseImpl implements CreateBarberUseCase {
    private final GenerateRandomId generateRandomId;
    private BarberRepository barberRepository;
    private CreateUserUseCase createUserUseCase;

    @Override
    public CreateBarberResponse createBarber(CreateBarberRequest request) {
        BarberEntity barber = createBarberEntity(request);

        return CreateBarberResponse.builder()
                .id(barber.getId())
                .build();
    }

    private BarberEntity createBarberEntity(CreateBarberRequest request) {
        int randomId = generateUniqueRandomId();
        CreateUserRequest userRequest = CreateUserRequestConverter.convert(request);

        UserEntity user = createUserUseCase.saveNewUser(userRequest);

        BarberEntity newBarber = BarberEntity.builder()
                .id(randomId)
                .user(user)
                .hire_date(request.getHire_date())
                .is_admin(request.is_admin())
                .build();

        return barberRepository.save(newBarber);
    }

    private int generateUniqueRandomId() {
        int randomId;

        do {
            randomId = generateRandomId.generate(100000, 900000);
        } while (barberRepository.existsById(Long.valueOf(randomId)));

        return randomId;
    }
}
