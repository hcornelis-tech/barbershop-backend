package nl.fontys.s3.clipfadeapplication.business.impl;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateRandomIdTest {
    private final GenerateRandomId generateRandomId = new GenerateRandomId();

    @Test
    void generateRandomId_shouldReturnTheGeneratedId() {
        int min = 1;
        int max = 100;

        int randomId = generateRandomId.generate(min, max);

        assertTrue(randomId >= min && randomId < max);
    }
}
