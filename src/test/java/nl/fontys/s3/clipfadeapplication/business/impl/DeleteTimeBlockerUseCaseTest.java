package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.persistence.TimeBlockerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTimeBlockerUseCaseTest {
    @Mock
    private TimeBlockerRepository timeBlockerRepository;

    @InjectMocks
    private DeleteTimeBlockerUseCaseImpl deleteTimeBlockerUseCase;

    @Test
    void deleteTimeBlocker() {
        int id = 1;

        deleteTimeBlockerUseCase.deleteTimeBlocker(id);

        verify(timeBlockerRepository).deleteById(Long.valueOf(id));
    }
}
