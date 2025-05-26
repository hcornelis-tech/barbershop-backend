package nl.fontys.s3.clipfadeapplication.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExists extends ResponseStatusException {
    public EmailAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, "Email already exists");
    }
}
