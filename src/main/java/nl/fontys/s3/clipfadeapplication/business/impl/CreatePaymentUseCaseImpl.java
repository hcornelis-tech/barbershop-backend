package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreatePaymentUseCase;
import nl.fontys.s3.clipfadeapplication.business.exception.InvalidAppointmentException;
import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreatePaymentResponse;
import nl.fontys.s3.clipfadeapplication.persistence.AppointmentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.PaymentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.AppointmentEntity;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreatePaymentUseCaseImpl implements CreatePaymentUseCase {
    private PaymentRepository paymentRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    public PaymentEntity createPayment(CreatePaymentRequest request, int appointmentId) {
        long dateTime = System.currentTimeMillis();
        UUID randomUUID;

        Optional<AppointmentEntity> appointment = appointmentRepository.findById(Long.valueOf(appointmentId));
        if(appointment.isEmpty()) {
            throw new InvalidAppointmentException("Appointment not found!");
        }

        do {
            randomUUID = UUID.randomUUID();
        } while (paymentRepository.existsById(randomUUID.toString()));

        PaymentEntity payment = PaymentEntity.builder()
                .id(randomUUID.toString())
                .appointment(appointment.get())
                .amount(request.getAmount())
                .date_time(String.valueOf(dateTime))
                .method(request.getMethod())
                .status("Completed")
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public CreatePaymentResponse paymentResponse(PaymentEntity payment) {
        return CreatePaymentResponse.builder()
                .id(payment.getId())
                .build();
    }
}
