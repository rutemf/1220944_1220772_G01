package pt.psoft.g1.psoftg1.shared.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class Base65Service {

    private static final String BASE65_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/#";
    private static final int DEFAULT_LENGTH = 8;
    private static final int BASE = BASE65_ALPHABET.length();

    private final SecureRandom random = new SecureRandom();

    public String generateIdSQL() {
        String randomPart = generateRandomBase65();
        long timestamp = Instant.now().toEpochMilli();
        return randomPart + "-" + timestamp;
    }

    public String generateIdNoSQL() {
        String randomPart = generateRandomBase65();
        long timestamp = Instant.now().toEpochMilli();
        return timestamp + "-" + randomPart;
    }

    private String generateRandomBase65() {
        StringBuilder sb = new StringBuilder(Base65Service.DEFAULT_LENGTH);
        for (int i = 0; i < Base65Service.DEFAULT_LENGTH; i++) {
            int index = random.nextInt(BASE);
            sb.append(BASE65_ALPHABET.charAt(index));
        }
        return sb.toString();
    }
}


