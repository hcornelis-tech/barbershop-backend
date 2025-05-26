package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAppointmentException extends ResponseStatusException {
  public InvalidAppointmentException(String message) {
    super(HttpStatus.BAD_REQUEST, message);;
  }
}
