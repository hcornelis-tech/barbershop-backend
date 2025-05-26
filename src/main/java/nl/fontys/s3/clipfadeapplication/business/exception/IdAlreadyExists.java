package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdAlreadyExists extends ResponseStatusException {
    public IdAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, "Id already exists");
    }
}
