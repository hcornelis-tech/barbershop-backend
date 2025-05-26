package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteAppointmentUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DeleteAppointmentUseCaseImpl deleteAppointmentUseCase;

    @Test
    void deleteAppointment_ShouldDeleteAppointment() {
        int id = 1;

        deleteAppointmentUseCase.deleteAppointment(id);

        verify(appointmentRepository).deleteById(Long.valueOf(id));
    }
}
