package com.mboaeat.common;

import java.util.Random;

public class RandomPasswordGenerator {

    public static String DEFAULT_GENERATE_PSWD = generatePswd();

    public static final int NO_OF_CAPS_ALPHA = 2;
    public static final int NO_OF_DIGITS = 2;
    public static final int NO_OF_SPECIAL_CHARS = 1;
    public static final int MIN_LEN = 16;
    public static final int MAX_LEN = 16;

    public static final String SPECIAL_CHARS = "!@$_*-/";
    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";

    public static String generatePswd() {
        return generatePswd(MIN_LEN, MAX_LEN, NO_OF_CAPS_ALPHA, NO_OF_DIGITS, NO_OF_SPECIAL_CHARS);
    }

    public static String generatePswd(int minLen, int maxLen, int noOfCAPSAlpha,
                                      int noOfDigits, int noOfSplChars) {
        if (minLen > maxLen) {
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        }
        if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen) {
            throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        }
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPECIAL_CHARS.charAt(rnd.nextInt(SPECIAL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return new String(pswd);
    }

    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while (pswd[index = rnd.nextInt(len)] != 0) {
        }
        return index;
    }
}
