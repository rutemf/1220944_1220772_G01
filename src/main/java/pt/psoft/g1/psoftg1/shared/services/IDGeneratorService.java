package pt.psoft.g1.psoftg1.shared.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class IDGeneratorService {

    private static final String BASE65_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/#";
    private static final int DEFAULT_LENGTH = 8;
    private static final int BASE = BASE65_ALPHABET.length();

    private static final SecureRandom random = new SecureRandom();

    public static String generateIdSQL() {
        String randomPart = generateRandomNumeric6();
        return encodeBase65(randomPart);
    }

    public static String generateIdNoSQL() {
        String randomPart = generateRandomHex6();
        long timestamp = Instant.now().toEpochMilli();
        return timestamp + "-" + randomPart;
    }

    private static String generateRandomHex6() {
        int value = random.nextInt(0x1000000);
        return String.format("%06X", value);
    }

    private static String generateRandomNumeric6() {
        int value = random.nextInt(1_000_000);
        return String.format("%06d", value);
    }

    private static String encodeBase65(String numericString) {
        long number = Long.parseLong(numericString);
        StringBuilder encoded = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % BASE);
            encoded.insert(0, BASE65_ALPHABET.charAt(remainder));
            number /= BASE;
        }

        while (encoded.length() < DEFAULT_LENGTH) {
            encoded.insert(0, BASE65_ALPHABET.charAt(0));
        }

        return encoded.toString();
    }
}


