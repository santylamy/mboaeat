package com.mboaeat.common;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordGenerator {

    private static int ITERATIONS = 10000;
    private static int HASHWIDTH = 128;

    private PasswordGenerator(){}

    public static Pbkdf2PasswordEncoder encoder(CharSequence secret){
        return new Pbkdf2PasswordEncoder(secret, ITERATIONS, HASHWIDTH);
    }

    public static String passwd(String hash , String rawPassword){
        return new Pbkdf2PasswordEncoder(hash, ITERATIONS, HASHWIDTH).encode(rawPassword);
    }

    public static String passwd(String rawPassword){
        return new Pbkdf2PasswordEncoder(RandomPasswordGenerator.DEFAULT_GENERATE_PSWD, ITERATIONS, HASHWIDTH).encode(rawPassword);
    }

    public static String generatePswd(int minLen, int maxLen, int noOfCAPSAlpha,
                                      int noOfDigits, int noOfSplChars, String rawPassword){
        return new Pbkdf2PasswordEncoder(
                RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars), ITERATIONS, HASHWIDTH
                ).encode(rawPassword);
    }

    public static boolean isMatches(String newPassword, String hash, String password) {
        return PasswordGenerator.encoder(hash)
                .matches(newPassword, password);
    }
}
