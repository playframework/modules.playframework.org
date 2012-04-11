package test;

import org.fest.assertions.Assertions;
import play.db.ebean.Model;

public class ModulesAssertions extends Assertions {


    public static ValidationAssert assertThat(Model model) {
        return new ValidationAssert(model);
    }


}
