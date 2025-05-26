package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.GetPaymentsUseCase;
import nl.fontys.s3.clipfadeapplication.domain.Payment;
import nl.fontys.s3.clipfadeapplication.domain.response.GetPaymentsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.PaymentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPaymentsUseCaseImpl implements GetPaymentsUseCase {
    private PaymentRepository paymentRepository;

    @Override
    public GetPaymentsResponse getPayments() {
        List<PaymentEntity> result = paymentRepository.findAll();

        final GetPaymentsResponse getPaymentsResponse = new GetPaymentsResponse();
        List<Payment> payments = convertList(result);

        getPaymentsResponse.setPayments(payments);

        return getPaymentsResponse;
    }

    @Override
    public GetPaymentsResponse getPaymentsBetweenDates(String start_date, String end_date) {
        List<PaymentEntity> result = paymentRepository.findPaymentsBetweenDates(start_date, end_date);

        final GetPaymentsResponse getPaymentsResponse = new GetPaymentsResponse();
        List<Payment> payments = convertList(result);

        getPaymentsResponse.setPayments(payments);

        return getPaymentsResponse;
    }

    @Override
    public GetPaymentsResponse getPaymentsBetweenDatesByBarber(String start_date, String end_date, int barberId) {
        List<PaymentEntity> result = paymentRepository.findPaymentsBetweenDatesByBarber(start_date, end_date, barberId);

        final GetPaymentsResponse getPaymentsResponse = new GetPaymentsResponse();
        List<Payment> payments = convertList(result);

        getPaymentsResponse.setPayments(payments);

        return getPaymentsResponse;
    }

    private List<Payment> convertList(List<PaymentEntity> paymentEntityList) {
        return paymentEntityList
                .stream()
                .map(PaymentConverter::convert)
                .toList();
    }
}
