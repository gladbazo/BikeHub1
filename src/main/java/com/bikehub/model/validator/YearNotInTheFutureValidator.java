package com.bikehub.model.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearNotInTheFutureValidator implements ConstraintValidator<YearNotInTheFuture, Number> {
    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        } else {

            int currentYear = Year.now().getValue();

            return value.intValue() <= currentYear;
        }
    }
}
