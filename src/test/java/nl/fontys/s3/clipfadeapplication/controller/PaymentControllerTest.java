package nl.fontys.s3.clipfadeapplication.controller;

import nl.fontys.s3.clipfadeapplication.business.CreatePaymentUseCase;
import nl.fontys.s3.clipfadeapplication.business.GetPaymentsUseCase;
import nl.fontys.s3.clipfadeapplication.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.clipfadeapplication.domain.Payment;
import nl.fontys.s3.clipfadeapplication.domain.response.GetPaymentsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PaymentController.class)
@WithMockUser
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePaymentUseCase createPaymentUseCase;

    @MockBean
    private GetPaymentsUseCase getPaymentsUseCase;

    @MockBean
    private AccessTokenDecoder accessTokenDecoder;

    @Test
    void getPayments_shouldReturnResponseWithPaymentsArray() throws Exception {
        //Arrange
        GetPaymentsResponse response = GetPaymentsResponse.builder()
                .payments(getPayments())
                .build();

        when(getPaymentsUseCase.getPayments()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/payment")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payments").isArray());
    }

    @Test
    void getPaymentsBetweenDates_shouldReturnResponseWithPaymentsArray() throws Exception {
        //Arrange
        String startDate = "1733841518240";
        String endDate = "1736062171042";

        GetPaymentsResponse response = GetPaymentsResponse.builder()
                .payments(getPayments())
                .build();

        when(getPaymentsUseCase.getPaymentsBetweenDates(startDate, endDate)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/payment")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payments").isArray());
    }

    @Test
    void getPaymentsBetweenDatesByBarber_shouldReturnResponseWithPaymentsArray() throws Exception {
        //Arrange
        String startDate = "1733841518240";
        String endDate = "1736062171042";
        int barberId = 1;

        GetPaymentsResponse response = GetPaymentsResponse.builder()
                .payments(getPayments())
                .build();

        when(getPaymentsUseCase.getPaymentsBetweenDatesByBarber(startDate, endDate, barberId)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/payment")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("barber", String.valueOf(barberId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payments").isArray());
    }

    private List<Payment> getPayments() {
        return List.of(
                Payment.builder()
                        .id("3839-nfjnkjfwk-23o")
                        .amount(20)
                        .date_time("1735062171042")
                        .method("Card")
                        .status("Completed")
                        .build(),
                Payment.builder()
                        .id("3839-nfjnkjfwk-3901")
                        .amount(30)
                        .date_time("1733841518240")
                        .method("Card")
                        .status("Completed")
                        .build()
        );
    }
}
