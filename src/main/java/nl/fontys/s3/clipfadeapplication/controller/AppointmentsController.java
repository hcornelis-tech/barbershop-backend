package nl.fontys.s3.clipfadeapplication.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.clipfadeapplication.business.*;
import nl.fontys.s3.clipfadeapplication.domain.Appointment;
import nl.fontys.s3.clipfadeapplication.domain.request.CreateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.domain.request.UpdateAppointmentRequest;
import nl.fontys.s3.clipfadeapplication.domain.response.CreateAppointmentResponse;
import nl.fontys.s3.clipfadeapplication.domain.response.GetAppointmentsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentsController {
    private GetAppointmentsUseCase appointmentsUseCase;
    private GetAppointmentUseCase getAppointmentUseCase;
    private CreateAppointmentUseCase createAppointmentUseCase;
    private UpdateAppointmentUseCase updateAppointmentUseCase;
    private DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<GetAppointmentsResponse> getAppointments() {
        GetAppointmentsResponse response = appointmentsUseCase.getAppointments();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") int id) {
        Optional<Appointment> appointment = getAppointmentUseCase.getAppointment(id);
        if (appointment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(appointment.get());
    }

    @GetMapping("/barber/{id}")
    public ResponseEntity<GetAppointmentsResponse> getBarberAppointments(@PathVariable("id") int id) {
        GetAppointmentsResponse response = appointmentsUseCase.getBarberAppointments(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"barber", "date"})
    public ResponseEntity<GetAppointmentsResponse> getBarberAppointmentsByDate(@RequestParam("barber") int barberId, @RequestParam("date") String date) {
        GetAppointmentsResponse response = appointmentsUseCase.getBarberAppointmentsByDate(barberId, date);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"startDate", "endDate", "barber"})
    public ResponseEntity<GetAppointmentsResponse> getPaymentsBetweenDatesByBarber(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("barber") int barberId) {
        GetAppointmentsResponse response = appointmentsUseCase.getBarberAppointmentsBetweenDates(startDate, endDate, barberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/barber/recent/{id}")
    public ResponseEntity<GetAppointmentsResponse> getBarberRecentAppointments(@PathVariable("id") int id) {
        GetAppointmentsResponse response = appointmentsUseCase.getBarberRecentAppointments(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<GetAppointmentsResponse> getUserAppointments(@PathVariable("id") int id) {
        GetAppointmentsResponse response = appointmentsUseCase.getUserAppointments(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest request) {
        Appointment appointment = createAppointmentUseCase.createAppointment(request);

        messagingTemplate.convertAndSend("/topic/appointments", appointment);

        CreateAppointmentResponse response = CreateAppointmentResponse.builder()
                .id(appointment.getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAppointment(@PathVariable("id") int id, @RequestBody @Valid UpdateAppointmentRequest request) {
        request.setId(id);
        Appointment appointment = updateAppointmentUseCase.updateAppointment(request);

        messagingTemplate.convertAndSend("/topic/appointments", appointment);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") int id) {
        deleteAppointmentUseCase.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
