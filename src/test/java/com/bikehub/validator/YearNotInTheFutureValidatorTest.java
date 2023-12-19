package com.bikehub.validator;

import com.bikehub.model.validator.YearNotInTheFutureValidator;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YearNotInTheFutureValidatorTest {

    private final YearNotInTheFutureValidator validator = new YearNotInTheFutureValidator();

    @Test
    public void testIsValid_WithNullValue() {
        assertTrue(validator.isValid(null, null)); // Null value should be considered valid
    }

    @Test
    public void testIsValid_WithYearInThePast() {
        int currentYear = Year.now().getValue();

        // Test with a year in the past
        int yearInPast = currentYear - 1;
        assertTrue(validator.isValid(yearInPast, null)); // A year in the past should be considered valid
    }

    @Test
    public void testIsValid_WithCurrentYear() {
        int currentYear = Year.now().getValue();

        // Test with the current year
        assertTrue(validator.isValid(currentYear, null)); // The current year should be considered valid
    }

    @Test
    public void testIsValid_WithFutureYear() {
        int currentYear = Year.now().getValue();

        // Test with a year in the future
        int futureYear = currentYear + 1;
        assertFalse(validator.isValid(futureYear, null)); // A year in the future should be considered invalid
    }
}
