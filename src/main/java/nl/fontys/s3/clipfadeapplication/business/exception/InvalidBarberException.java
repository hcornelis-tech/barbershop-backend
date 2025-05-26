package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidBarberException extends ResponseStatusException {
  public InvalidBarberException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
