package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.response.GetPaymentsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.PaymentRepository;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPaymentsUseCaseImplTest {
    @Mock
    PaymentRepository paymentRepository;

    @Test
    void getPayments_shouldReturnAllPayments() {
        //Arrange
        PaymentEntity payment1 = getPayment("1736876617891");
        PaymentEntity payment2 = getPayment("1736848107692");

        List<PaymentEntity> expectedPayments = List.of(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        GetPaymentsUseCaseImpl getPaymentsUseCase = new GetPaymentsUseCaseImpl(paymentRepository);

        //Act
        GetPaymentsResponse response = getPaymentsUseCase.getPayments();

        //Assert
        assertEquals(expectedPayments.size(), response.getPayments().size());
    }

    @Test
    void getPaymentsBetweenDates_shouldReturnPayments() {
        //Arrange
        PaymentEntity payment1 = getPayment("1736876617891");
        PaymentEntity payment2 = getPayment("1736848107692");
        PaymentEntity payment3 = getPayment("1736211512702");

        List<PaymentEntity> expectedPayments = List.of(payment1, payment2, payment3);

        when(paymentRepository.findPaymentsBetweenDates("1736211512702", "1736876617891")).thenReturn(expectedPayments);

        GetPaymentsUseCaseImpl getPaymentsUseCase = new GetPaymentsUseCaseImpl(paymentRepository);

        //Act
        GetPaymentsResponse response = getPaymentsUseCase.getPaymentsBetweenDates("1736211512702", "1736876617891");

        //Assert
        assertEquals(expectedPayments.size(), response.getPayments().size());
    }

    @Test
    void getPaymentsBetweenDatesByBarber_shouldReturnPayments() {
        //Arrange
        PaymentEntity payment1 = getPayment("1736876617891");
        PaymentEntity payment2 = getPayment("1736848107692");
        PaymentEntity payment3 = getPayment("1736211512702");

        List<PaymentEntity> expectedPayments = List.of(payment1, payment2, payment3);

        when(paymentRepository.findPaymentsBetweenDatesByBarber("1736211512702", "1736876617891", 1)).thenReturn(expectedPayments);

        GetPaymentsUseCaseImpl getPaymentsUseCase = new GetPaymentsUseCaseImpl(paymentRepository);

        //Act
        GetPaymentsResponse response = getPaymentsUseCase.getPaymentsBetweenDatesByBarber("1736211512702", "1736876617891", 1);

        //Assert
        assertEquals(expectedPayments.size(), response.getPayments().size());
    }

    private PaymentEntity getPayment(String dateTime) {
        return PaymentEntity.builder()
                .id("1")
                .amount(20)
                .date_time(dateTime)
                .method("Card")
                .status("Completed")
                .build();
    }
}
