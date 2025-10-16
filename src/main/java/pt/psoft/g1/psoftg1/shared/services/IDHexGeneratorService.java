package pt.psoft.g1.psoftg1.shared.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
@Profile("hex")
public class IDHexGeneratorService implements IDGeneratorService {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generateId() {
        String randomPart = generateRandomHex6();
        long timestamp = Instant.now().toEpochMilli();
        return timestamp + "-" + randomPart;
    }

    private static String generateRandomHex6() {
        int value = random.nextInt(0x1000000);
        return String.format("%06X", value);
    }
}
