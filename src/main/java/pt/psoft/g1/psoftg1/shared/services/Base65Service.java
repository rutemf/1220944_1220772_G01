package pt.psoft.g1.psoftg1.shared.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Base65Service {

    private static final String BASE65_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/#";
    private static final int BASE = BASE65_ALPHABET.length();

    public String encodeString(String input) {
        byte[] data = input.getBytes(StandardCharsets.UTF_8);
        return encode(data);
    }

    public String decodeString(String base65Encoded) {
        byte[] decodedBytes = decode(base65Encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private String encode(byte[] data) {
        BigInteger num = new BigInteger(1, data);

        if (num.equals(BigInteger.ZERO)) {
            return String.valueOf(BASE65_ALPHABET.charAt(0));
        }

        StringBuilder encoded = new StringBuilder();

        while (num.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = num.divideAndRemainder(BigInteger.valueOf(BASE));
            num = divmod[0];
            int remainder = divmod[1].intValue();
            encoded.insert(0, BASE65_ALPHABET.charAt(remainder));
        }

        return encoded.toString();
    }

    private byte[] decode(String encoded) {
        BigInteger num = BigInteger.ZERO;

        for (char c : encoded.toCharArray()) {
            int index = BASE65_ALPHABET.indexOf(c);
            if (index == -1) {
                throw new IllegalArgumentException("Character not available at Base65: " + c);
            }
            num = num.multiply(BigInteger.valueOf(BASE)).add(BigInteger.valueOf(index));
        }

        byte[] decodedBytes = num.toByteArray();

        if (decodedBytes.length > 1 && decodedBytes[0] == 0) {
            byte[] tmp = new byte[decodedBytes.length - 1];
            System.arraycopy(decodedBytes, 1, tmp, 0, tmp.length);
            decodedBytes = tmp;
        }

        return decodedBytes;
    }
}
