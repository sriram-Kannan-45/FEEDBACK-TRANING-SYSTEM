package com.trainingfeedback.util;

import com.trainingfeedback.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void testValidateId_ValidId() {
        assertDoesNotThrow(() -> ValidationUtil.validateId(1));
        assertDoesNotThrow(() -> ValidationUtil.validateId(100));
    }

    @Test
    void testValidateId_InvalidId() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateId(0));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateId(-1));
    }

    @Test
    void testValidateName_ValidName() {
        assertDoesNotThrow(() -> ValidationUtil.validateName("John Doe"));
        assertDoesNotThrow(() -> ValidationUtil.validateName("Alice"));
    }

    @Test
    void testValidateName_InvalidName() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateName(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateName("   "));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateName("John123"));
    }

    @Test
    void testValidateEmail_ValidEmail() {
        assertDoesNotThrow(() -> ValidationUtil.validateEmail("test@example.com"));
        assertDoesNotThrow(() -> ValidationUtil.validateEmail("user.name@domain.org"));
    }

    @Test
    void testValidateEmail_InvalidEmail() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateEmail(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateEmail("invalid"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateEmail("test@"));
    }

    @Test
    void testValidatePassword_ValidPassword() {
        assertDoesNotThrow(() -> ValidationUtil.validatePassword("pass123"));
        assertDoesNotThrow(() -> ValidationUtil.validatePassword("abc123"));
    }

    @Test
    void testValidatePassword_InvalidPassword() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validatePassword(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validatePassword("12345"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validatePassword("abcdef"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validatePassword("abcde"));
    }

    @Test
    void testValidateRating_ValidRating() {
        assertDoesNotThrow(() -> ValidationUtil.validateRating(1));
        assertDoesNotThrow(() -> ValidationUtil.validateRating(3));
        assertDoesNotThrow(() -> ValidationUtil.validateRating(5));
    }

    @Test
    void testValidateRating_InvalidRating() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRating(0));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRating(6));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateRating(-1));
    }

    @Test
    void testValidateDate_ValidDate() {
        assertDoesNotThrow(() -> ValidationUtil.validateDate("01/01/2024"));
        assertDoesNotThrow(() -> ValidationUtil.validateDate("31/12/2024"));
        assertDoesNotThrow(() -> ValidationUtil.validateDate("29/02/2024")); // Leap year
    }

    @Test
    void testValidateDate_InvalidDate() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDate(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDate("01-01-2024"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDate("32/01/2024"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDate("31/02/2024"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDate("13/13/2024"));
    }

    @Test
    void testValidateTime_ValidTime() {
        assertDoesNotThrow(() -> ValidationUtil.validateTime("09:00"));
        assertDoesNotThrow(() -> ValidationUtil.validateTime("23:59"));
        assertDoesNotThrow(() -> ValidationUtil.validateTime("12:30"));
    }

    @Test
    void testValidateTime_InvalidTime() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateTime(""));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateTime("25:00"));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateTime("12:60"));
    }

    @Test
    void testValidateDuration_ValidDuration() {
        assertDoesNotThrow(() -> ValidationUtil.validateDuration(1));
        assertDoesNotThrow(() -> ValidationUtil.validateDuration(8));
    }

    @Test
    void testValidateDuration_InvalidDuration() {
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDuration(0));
        assertThrows(InvalidInputException.class, () -> ValidationUtil.validateDuration(-1));
    }
}