package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.DeleteUserUseCase;
import nl.fontys.s3.clipfadeapplication.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private UserRepository userRepository;

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(Long.valueOf(id));
    }
}
