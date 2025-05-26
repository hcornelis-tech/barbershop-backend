package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.CreatePaymentUseCase;
import nl.fontys.s3.clipfadeapplication.business.GetPaymentsUseCase;
import nl.fontys.s3.clipfadeapplication.domain.request.CreatePaymentRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreatePaymentResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetPaymentsResponse;
import nl.fontys.s3.clipfadeapplication.persistence.entity.PaymentEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    private CreatePaymentUseCase createPaymentUseCase;
    private GetPaymentsUseCase getPaymentsUseCase;

    @GetMapping
    public ResponseEntity<GetPaymentsResponse> getPayments() {
        GetPaymentsResponse response = getPaymentsUseCase.getPayments();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<GetPaymentsResponse> getPaymentsBetweenDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        GetPaymentsResponse response = getPaymentsUseCase.getPaymentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"startDate", "endDate", "barber"})
    public ResponseEntity<GetPaymentsResponse> getPaymentsBetweenDatesByBarber(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("barber") int barberId) {
        GetPaymentsResponse response = getPaymentsUseCase.getPaymentsBetweenDatesByBarber(startDate, endDate, barberId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("{appointment_id}")
    public ResponseEntity<CreatePaymentResponse> createPayment(@PathVariable("appointment_id") int id, @RequestBody @Valid CreatePaymentRequest request) {
        PaymentEntity payment = createPaymentUseCase.createPayment(request, id);
        CreatePaymentResponse response = createPaymentUseCase.paymentResponse(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
