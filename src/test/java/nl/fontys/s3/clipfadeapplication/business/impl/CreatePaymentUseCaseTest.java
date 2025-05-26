package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.business.exception.InvalidAppointmentException;
import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreatePaymentResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.PaymentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePaymentUseCaseTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private CreatePaymentUseCaseImpl createPaymentUseCase;

    @Test
    void createPayment_shouldReturnPaymentCreatedSuccessfully() {
        //Arrange
        CreatePaymentRequest request = createPaymentRequest();

        AppointmentEntity savedAppointment = AppointmentEntity.builder()
                .id(1)
                .build();

        PaymentEntity expectedPayment = PaymentEntity.builder()
                .amount(20)
                .date_time("2024-12-06")
                .method("Card")
                .status("Completed")
                .build();

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(savedAppointment));
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(expectedPayment);

        //Act
        PaymentEntity actualPayment = createPaymentUseCase.createPayment(request, 1);

        //Assert
        assertEquals(expectedPayment.getId(), actualPayment.getId());
    }

    @Test
    void createPayment_shouldThrowInvalidAppointmentException_whenAppointmentDoesNotExist() {
        // Arrange
        int appointmentId = 1;
        CreatePaymentRequest request = createPaymentRequest();
        when(appointmentRepository.findById(Long.valueOf(appointmentId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidAppointmentException.class, () -> createPaymentUseCase.createPayment(request, 1));
    }

    @Test
    void createPayment_shouldReturnCreatePaymentResponse() {
        //Arrange
        PaymentEntity payment = PaymentEntity.builder()
                .id("123")
                .amount(20)
                .date_time("2024-12-06")
                .method("Card")
                .status("Completed")
                .build();

        //Act
        CreatePaymentResponse response = createPaymentUseCase.paymentResponse(payment);

        //Assert
        assertEquals(payment.getId(), response.getId());
    }

    private CreatePaymentRequest createPaymentRequest() {
        return CreatePaymentRequest.builder()
                .amount(20)
                .method("Card")
                .build();
    }
}
