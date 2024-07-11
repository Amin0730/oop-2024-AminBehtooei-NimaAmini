package org.example.Tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomPasswordGenerator {
    public static String generateRandomString() {
        char[] uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] lowercaseLetters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] digits = "0123456789".toCharArray();
        char[] specialChars = "@#*&".toCharArray();

        int randomIndexUppercase = new Random().nextInt(uppercaseLetters.length);
        int randomIndexLowercase = new Random().nextInt(lowercaseLetters.length);
        int randomIndexDigit = new Random().nextInt(digits.length);
        int randomIndexSpecial = new Random().nextInt(specialChars.length);

        char[] initialChars = {uppercaseLetters[randomIndexUppercase], lowercaseLetters[randomIndexLowercase],
                digits[randomIndexDigit], specialChars[randomIndexSpecial]};

        while (initialChars.length < 8) {
            initialChars = Arrays.copyOf(initialChars, initialChars.length + 1);
            int randomIndexChar = new Random().nextInt(62);
            if (randomIndexChar < 26) {
                initialChars[initialChars.length - 1] = uppercaseLetters[randomIndexChar];
            } else if (randomIndexChar < 52) {
                initialChars[initialChars.length - 1] = lowercaseLetters[randomIndexChar - 26];
            } else {
                initialChars[initialChars.length - 1] = digits[randomIndexChar - 52];
            }
        }

        Collections.shuffle(Arrays.asList(initialChars));

        return new String(initialChars);
    }
}
