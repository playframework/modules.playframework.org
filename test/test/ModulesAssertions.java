package test;

import org.fest.assertions.Assertions;
import play.db.ebean.Model;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ModulesAssertions extends Assertions {


    public static ValidationAssert assertThat(Model model) {
        return new ValidationAssert(model);
    }

    public static ConstraintViolationsAssert assertThat(Set<ConstraintViolation<Object>> violations) {
        return new ConstraintViolationsAssert(violations);
    }


}
