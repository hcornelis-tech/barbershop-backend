package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.DeleteBarberUseCase;
import nl.fontys.s3.clipfadeapplication.business.DeleteUserUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidBarberException;
import nl.fontys.s3.clipfadeapplication.persistence.BarberRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.BarberEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteBarberUseCaseImpl implements DeleteBarberUseCase {
    private BarberRepository barberRepository;
    private DeleteUserUseCase deleteUserUseCase;

    @Override
    public void deleteBarber(int id) {
        Optional<BarberEntity> optionalBarber = barberRepository.findById(Long.valueOf(id));
        if (optionalBarber.isEmpty()) {
            throw new InvalidBarberException("Barber not found");
        }

        BarberEntity barber = optionalBarber.get();
        int userId = barber.getUser().getId();

        barberRepository.deleteById(Long.valueOf(id));

        if (!barberRepository.existsById(Long.valueOf(id))) {
            deleteUserUseCase.deleteUser(userId);
        }
    }
}
