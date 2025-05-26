package nl.fontys.s3.clipfadeapplication.business;

import nl.fontys.s3.clipfadeapplication.domain.response.GetPaymentsResponse;

public interface GetPaymentsUseCase {
    public GetPaymentsResponse getPayments();
    public GetPaymentsResponse getPaymentsBetweenDates(String start_date, String end_date);
    public GetPaymentsResponse getPaymentsBetweenDatesByBarber(String start_date, String end_date, int barberId);
}
