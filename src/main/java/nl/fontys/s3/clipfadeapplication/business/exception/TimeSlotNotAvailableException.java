package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TimeSlotNotAvailableException extends ResponseStatusException {
    public TimeSlotNotAvailableException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
