package com.pnk.bankapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnk.bankapi.exception.AssertionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;


public class Utils {

    private static final Logger logger = LogManager.getLogger(Utils.class);

    public static String convertToUSDollar(double amount) {
        NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);

        return usdFormat.format(amount);
    }


    public static String convertToUSDollar(BigDecimal amount) {
        NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);

        return usdFormat.format(amount);
    }


    public static String convertToUSDollarWithoutDollarSign(double amount) {
        Locale locale = Locale.US;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String formattedAmount = currencyFormatter.format(amount);

        return formattedAmount.replace("$", "");        // Removing the dollar sign
    }


    public static String convertToUSDollarWithoutDollarSign(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        return decimalFormat.format(amount);        // Output: 12,345.68
    }


    public static boolean isStringEmpty(String stringInput) {
        return stringInput == null || stringInput.trim().isEmpty();
    }


    public static void assertNull(boolean aValue) {
        assertNull(aValue, "assertNull failed");
    }


    public static void assertNull(boolean aValue, String aMessage) {
        if (!aValue) {
            throw new AssertionFailedException(aMessage);
        }
    }


    public static void assertNull(Object aValue, String aMessage) {
        assertNull((aValue != null), aMessage);
    }


    public static void assertTrue(boolean aValue) {
        assertTrue(aValue, "assertTrue failed");
    }


    public static void assertTrue(boolean aValue, String aMessage) {
        if (!aValue) {
            throw new AssertionFailedException(aMessage);
        }
    }


    public static boolean isFieldMissing(Object objectInput, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> retrievedClass = objectInput.getClass();

        Field field = retrievedClass.getDeclaredField(fieldName);

        field.setAccessible(true);

        return field.get(objectInput) == null;
    }


    public static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }


    public static UUID generateUUID() {
        return UUID.randomUUID();
    }


    public static UUID convertStringToUUID(String uuidStr) {
        return UUID.fromString(uuidStr);
    }


    public static <T> List<T> convertJsonToObjectList(Class<T> objectType, String jsonInput) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonInput, new TypeReference<>() {
            });
        } catch (Exception e) {
            logger.error("Error converting JSON to object list: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    public static String generateRandomString(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length must be greater than zero.");
        }

        SecureRandom random = new SecureRandom();

        // Define character sets for each requirement
        String numbers = "0123456789";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialChars = "!@#$%^&*()-_+=<>?/[]{}|";

        // Generate at least one character from each character set
        char randomNum = numbers.charAt(random.nextInt(numbers.length()));
        char randomLower = lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length()));
        char randomUpper = upperCaseChars.charAt(random.nextInt(upperCaseChars.length()));
        char randomSpecial = specialChars.charAt(random.nextInt(specialChars.length()));

        // Combine all character sets and create the full random string
        String allChars = numbers + lowerCaseChars + upperCaseChars + specialChars;
        StringBuilder randomStrBuilder = new StringBuilder();

        // Add the characters that meet each requirement first
        randomStrBuilder.append(randomNum);
        randomStrBuilder.append(randomLower);
        randomStrBuilder.append(randomUpper);
        randomStrBuilder.append(randomSpecial);

        // Fill in the remaining characters randomly to get a string of length 8
        for (int i = 4; i < len; i++) {
            char randomChar = allChars.charAt(random.nextInt(allChars.length()));
            randomStrBuilder.append(randomChar);
        }

        // Convert the StringBuilder to a String and return
        return randomStrBuilder.toString();
    }


    public static boolean validateEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        // Compile the regex pattern only once for better performance
        Pattern pattern = Pattern.compile(regexPattern);
        // Use Matcher.find() instead of Matcher.matches() for flexibility
        return pattern.matcher(emailAddress.toLowerCase()).matches();
    }


    public static String generateRandomStringAZ09(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        // Define the characters that can be part of the string.
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Loop through and append random characters to the StringBuilder.
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }


    /**
     * Copies matching fields from source object to target object.
     *
     * @param source The source object.
     * @param target The target object.
     * @param <T>    The type of the source and target object.
     * @throws IllegalAccessException When field access fails.
     */
    public static <T> void copyProperties(T source, T target) throws IllegalAccessException {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target objects must not be null.");
        }

        // Check that source and target are of the same type
        if (!source.getClass().equals(target.getClass())) {
            throw new IllegalArgumentException("Source and target must be in the same type.");
        }

        // Get declared fields of the class
        Field[] fields = source.getClass().getDeclaredFields();

        // Loop through fields to copy properties
        for (Field field : fields) {
            field.setAccessible(true); // Make private fields accessible
            Object value = field.get(source);
            if (value != null) {
                field.set(target, value);
            }
        }
    }
}
