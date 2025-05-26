package nl.fontys.s3.clipfadeapplication.business.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class GenerateRandomId {
    public int generate(int min, int max) {
        SecureRandom random = new SecureRandom();

        return min + random.nextInt(max);
    }
}
