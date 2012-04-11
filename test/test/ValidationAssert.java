package test;

import org.fest.assertions.GenericAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static play.data.validation.Validation.getValidator;
import static test.ModulesAssertions.assertThat;

public class ValidationAssert extends GenericAssert<ValidationAssert, Object> {

    public ValidationAssert(Object model) {
        super(ValidationAssert.class, model);
    }

    public ValidationAssert isValid() {
        assertThat(validate()).isEmpty();
        return this; 
    }

    private Set<ConstraintViolation<Object>> validate() {
        return getValidator().validate(actual);
    }

    public ValidationAssert isInvalidBecause(String fieldName, String violationMessage) {
        assertThat(validate()).containsError(fieldName, violationMessage);
        return this;
    }

    public ValidationAssert isInvalidBecauseMissingRequired(String fieldName){
        return isInvalidBecause(fieldName, "error.required");
    }
}
