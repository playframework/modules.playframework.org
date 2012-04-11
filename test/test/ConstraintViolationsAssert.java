package test;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.fest.assertions.GenericAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class ConstraintViolationsAssert extends GenericAssert<ConstraintViolationsAssert, Set<ConstraintViolation<Object>>> {

    private boolean empty;

    public ConstraintViolationsAssert(Set<ConstraintViolation<Object>> violations) {
        super(ConstraintViolationsAssert.class, violations);
    }

    public ConstraintViolationsAssert containsError(final String fieldName, final String violationMessage) {
        assertThat(Collections2.filter(actual, new Predicate<ConstraintViolation<Object>>() {
            @Override
            public boolean apply(ConstraintViolation<Object> violation) {
                return violation.getPropertyPath().toString().equals(fieldName) && violation.getMessage().equals(violationMessage);
            }
        })).isNotEmpty();
        return this;
    }

    public ConstraintViolationsAssert isEmpty() {
        assertThat(actual).isEmpty();
        return this;
    }
}
