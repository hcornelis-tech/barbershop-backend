package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Payment;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentConverterTest {
    @Test
    void convert() {
        //Arrange
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id("1")
                .amount(20)
                .date_time("2024-12-06")
                .method("Card")
                .status("Completed")
                .build();

        Payment payment = PaymentConverter.convert(paymentEntity);

        //Assert
        assertEquals(paymentEntity.getId(), payment.getId());
        assertEquals(paymentEntity.getMethod(), payment.getMethod());
        assertEquals(paymentEntity.getStatus(), payment.getStatus());
    }
}
