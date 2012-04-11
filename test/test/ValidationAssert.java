package test;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static play.data.validation.Validation.getValidator;

public class ValidationAssert extends GenericAssert<ValidationAssert, Object> {

    public ValidationAssert(Object model) {
        super(ValidationAssert.class, model);
    }

    public void isValid() {
        Set<ConstraintViolation<Object>> violationSet = getValidator().validate(actual);
        Assertions.assertThat(violationSet).isEmpty();
    }
}
