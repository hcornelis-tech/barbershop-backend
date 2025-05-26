package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreatePaymentResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;

public interface CreatePaymentUseCase {
    PaymentEntity createPayment(CreatePaymentRequest request, int appointmentId);
    CreatePaymentResponse paymentResponse(PaymentEntity payment);
}
