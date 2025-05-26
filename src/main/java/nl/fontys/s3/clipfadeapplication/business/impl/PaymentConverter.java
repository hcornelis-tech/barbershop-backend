package nl.fontys.s3.clipfadeapplication.business.impl;

import nl.fontys.s3.clipfadeapplication.domain.Payment;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;

final class PaymentConverter {
    private PaymentConverter() {}

    public static Payment convert(PaymentEntity paymentEntity) {
        return Payment.builder()
                .id(paymentEntity.getId())
                .amount(paymentEntity.getAmount())
                .date_time(paymentEntity.getDate_time())
                .method(paymentEntity.getMethod())
                .status(paymentEntity.getStatus())
                .build();
    }
}
