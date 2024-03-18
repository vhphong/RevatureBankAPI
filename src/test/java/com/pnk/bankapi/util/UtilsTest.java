package com.pnk.bankapi.util;

import com.pnk.bankapi.exception.AssertionFailedException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.pnk.bankapi.util.Utils.convertToUSDollar;
import static com.pnk.bankapi.util.Utils.validateEmail;
import static org.junit.Assert.*;


public class UtilsTest {

    @Test
    public void testConvertToUSDollar_double() {
        double amount = 100.50;
        String expected = "$100.50";
        String actual = convertToUSDollar(amount);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertToUSDollar_BigDecimal() {
        // Test case 1: Convert 100 USD to USD
        BigDecimal amount1 = new BigDecimal("100");
        String expected1 = "$100.00";
        String result1 = convertToUSDollar(amount1);
        assertEquals(expected1, result1);

        // Test case 2: Convert 0 USD to USD
        BigDecimal amount2 = BigDecimal.ZERO;
        String expected2 = "$0.00";
        String result2 = convertToUSDollar(amount2);
        assertEquals(expected2, result2);

        // Test case 3: Convert 1234.5678 USD to USD
        BigDecimal amount3 = new BigDecimal("1234.5678");
        String expected3 = "$1,234.57";
        String result3 = convertToUSDollar(amount3);
        assertEquals(expected3, result3);

        // Test case 4: Convert negative amount -500 USD to USD
        BigDecimal amount4 = new BigDecimal("-500");
        String expected4 = "-$500.00";
        String result4 = convertToUSDollar(amount4);
        assertEquals(expected4, result4);
    }


    @Test
    public void testConvertToUSDollarWithoutDollarSign_double() {
        double amount = 1234.56;
        String expected = "1,234.56";
        String result = Utils.convertToUSDollarWithoutDollarSign(amount);

        assertEquals(expected, result);

        // testConvertToUSDollarWithoutDollarSignWithZeroAmount
        amount = 0.0;
        expected = "0.00";
        result = Utils.convertToUSDollarWithoutDollarSign(amount);

        assertEquals(expected, result);

        // testConvertToUSDollarWithoutDollarSignWithNegativeAmount
        amount = -1000.50;
        expected = "-1,000.50";
        result = Utils.convertToUSDollarWithoutDollarSign(amount);

        assertEquals(expected, result);
    }


    @Test
    public void testConvertToUSDollarWithoutDollarSign_BigDecimal() {
        // Arrange
        BigDecimal amount = new BigDecimal("12345.6789");
        String expectedOutput = "12,345.68";

        // Act
        String actualOutput = Utils.convertToUSDollarWithoutDollarSign(amount);

        // Assert
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testIsStringEmpty() {
        String emptyString = "";
        assertTrue(Utils.isStringEmpty(emptyString));

        // testNonEmptyString
        String nonEmptyString = "Hello, World!";
        assertFalse(Utils.isStringEmpty(nonEmptyString));

        // testNullString
        assertTrue(Utils.isStringEmpty(null));
    }


    @Test
    public void testAssertNull() {
        try {
            // Test case 1: Passing false should throw AssertionFailedException
            Utils.assertNull(false);
            // If the above line does not throw an exception, the test case fails
            fail("AssertionFailedException was not thrown.");
        } catch (AssertionFailedException e) {
            // Test passed
        }

        try {
            // Test case 2: Passing true should not throw any exception
            Utils.assertNull(true);
            // If the above line throws an exception, the test case fails
        } catch (AssertionFailedException e) {
            fail("AssertionFailedException was thrown incorrectly.");
        }

        try {
            // Test case 3: Passing false with a custom message should throw AssertionFailedException with the custom message
            String customMessage = "Custom message";
            try {
                Utils.assertNull(false, customMessage);
                fail("AssertionFailedException was not thrown.");
            } catch (AssertionFailedException e) {
                Assert.assertEquals(customMessage, e.getMessage());
            }
        } catch (AssertionFailedException e) {
            fail("AssertionFailedException was thrown incorrectly.");
        }
    }


    @Test
    public void testAssertNull_Object() {
        // Test case 1: Passing non-null value should fail the assertion
        Object nonNullValue = new Object();

        Utils.assertNull(nonNullValue, "Non-null value should fail the assertion");
    }


    @Test
    public void testAssertTrue() {
        // Test case 1: Passing true value
        Utils.assertTrue(true);

        // Test case 2: Passing false value
        try {
            Utils.assertTrue(false);
            // If the above line doesn't throw an exception, the test should fail
            assert false : "assertTrue failed to throw an exception for false value";
        } catch (AssertionFailedException e) {
            // The exception is expected for false value, so the test should pass
        }
    }


    @Test
    public void testIsFieldMissing() throws NoSuchFieldException, IllegalAccessException {
        // Create an object with a field.
        Customer object = new Customer();
        String fieldName = "name";

        // Check if the field is missing.
        boolean isMissing = Utils.isFieldMissing(object, fieldName);
        assertTrue(isMissing);

        // Set the field to a value.
        object.setName("John Doe");

        // Check if the field is no longer missing.
        isMissing = Utils.isFieldMissing(object, fieldName);
        assertFalse(isMissing);
    }


    @Test
    public void testGenerateUUIDString() {
        String uuidString = Utils.generateUUIDString();
        assertEquals(36, uuidString.length());

        UUID expected = UUID.fromString(uuidString);
        UUID actual = Utils.convertStringToUUID(uuidString);
        assertEquals(expected, actual);
    }


    @Test
    public void testGenerateUUID() {
        UUID uuid = Utils.generateUUID();
        assertFalse(uuid.toString().isEmpty());

        String uuidStr = uuid.toString();
        assertEquals(UUID.fromString(uuidStr).toString(), uuidStr);
    }


    @Test
    public void testConvertStringToUUID() {
        // valid UUID
        String validUUIDStr = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        UUID expected = UUID.fromString(validUUIDStr);
        UUID actual = Utils.convertStringToUUID(validUUIDStr);
        assertEquals(expected, actual);

        // invalid UUID
        String invalidUUIDStr = "invalid-uuid-string";
        assertThrows(IllegalArgumentException.class, () -> Utils.convertStringToUUID(invalidUUIDStr));
    }


    @Test
    public void testConvertJsonToList() {
        // Prepare test data
        Object objectInput = new Object();// = new YourObjectClass(); // Replace with your actual object class
        String jsonInput = """
                 [
                     {"key1": "value"},
                     {"key2": "anotherValue"}
                 ]
                """;

        // Call the method
        List<?> resultList = Utils.convertJsonToObjectList(objectInput.getClass(), jsonInput);

        // Assertions
        assertNotNull(resultList);
        assertEquals(2, resultList.size()); // Check if the correct number of items were converted
        assertEquals("{key1=value}", resultList.get(0).toString());
        assertEquals("{key2=anotherValue}", resultList.get(1).toString());

        // test case: invalid json
        String jsonInput_invalid = "{invalid-json}";
        List<?> result = Utils.convertJsonToObjectList(objectInput.getClass(), jsonInput_invalid);

        assertTrue(result.isEmpty());
    }


    @Test
    public void testGenerateRandomString() {
        // testStringLength
        int length = 10;
        String randomString = Utils.generateRandomString(length);
        assertEquals(length, randomString.length());

        // testContainsNumbers
        assertTrue(randomString.chars().anyMatch(Character::isDigit));

        // testContainsLowerCase
        assertTrue(randomString.chars().anyMatch(Character::isLowerCase));

        // testContainsUpperCase
        assertTrue(randomString.chars().anyMatch(Character::isUpperCase));

        // testContainsSpecialChar
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()-_+=<>?/\\[\\]{}|]");
        assertTrue(specialCharPattern.matcher(randomString).find());
    }


    @Test
    public void testValidateEmail() {
        // test case: valid email
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        assertTrue(validateEmail("username@domain.com"));
        assertFalse(validateEmail("username.@domain.com"));
        assertFalse(validateEmail(".username@domain.com"));
        assertFalse(validateEmail("user-name@domain.com."));
        assertFalse(validateEmail("username@.com"));
    }


    @Test
    public void testGenerateRandomStringAZ09() {
        int length = 100;
        String result = Utils.generateRandomStringAZ09(length);
        for (char c : result.toCharArray()) {
            assertTrue("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".indexOf(c) >= 0);
        }
    }


    @Test
    public void testCopyProperties() throws IllegalAccessException {
        // testCopyProperties_Valid
        Customer sourceCustomer = new Customer();
        sourceCustomer.setName("Phong");
        sourceCustomer.setPhone("800-test");

        Customer target = new Customer();
        Utils.copyProperties(sourceCustomer, target);

        assertEquals("Phong", target.getName());
        assertEquals("800-test", target.getPhone());

        // testCopyProperties_null_source
        assertThrows(IllegalArgumentException.class, () -> Utils.copyProperties(null, null));

        // testCopyProperties_different_type
        Account account = new Account();
        assertThrows(IllegalArgumentException.class, () -> Utils.copyProperties(sourceCustomer, account));
    }
}
