package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidServiceException extends ResponseStatusException {
    public InvalidServiceException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
