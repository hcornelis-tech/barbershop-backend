package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.DeleteTimeBlockerUseCase;
import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteTimeBlockerUseCaseImpl implements DeleteTimeBlockerUseCase {
    private TimeBlockerRepository timeBlockerRepository;

    @Override
    public void deleteTimeBlocker(int id) {
        timeBlockerRepository.deleteById(Long.valueOf(id));
    }
}
