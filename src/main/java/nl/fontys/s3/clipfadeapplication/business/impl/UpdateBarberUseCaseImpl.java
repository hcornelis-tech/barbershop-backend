package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.UpdateBarberUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateBarberRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateUserRequest;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateBarberUseCaseImpl implements UpdateBarberUseCase {
    private BarberRepository barberRepository;
    private UpdateUserUseCaseImpl updateUserUseCase;

    public void updateBarber(UpdateBarberRequest request) {
        Optional<BarberEntity> barberOptional = barberRepository.findById(Long.valueOf(request.getId()));
        if (barberOptional.isEmpty()) {
            throw new InvalidBarberException("Barber with id " + request.getId() + " not found");
        }

        BarberEntity barber = barberOptional.get();

        updateUser(barber, request);

        barber.setHire_date(request.getHire_date());
        barber.set_admin(request.is_admin());

        barberRepository.save(barber);
    }

    private void updateUser(BarberEntity barber, UpdateBarberRequest request) {
        UserEntity user = updateUserUseCase.getUser(barber.getUser().getId());

        UpdateUserRequest userRequest = UpdateUserRequestConverter.convert(request);
        updateUserUseCase.updateUserValues(user, userRequest);
    }
}
