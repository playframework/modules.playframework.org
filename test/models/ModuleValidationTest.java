package models;

import org.junit.Test;
import test.ModulesAssertions;

public class ModuleValidationTest {

    @Test
    public void valid() {
        Module module = buildValid();
        ModulesAssertions.assertThat(module).isValid();
    }

    private Module buildValid() {
        Module module = new Module();
        module.name = "moduleName";
        module.key = "moduleKey";
        module.summary = "module summary";
        module.description = "module description";
        module.licenseType = "license type";
        return module;
    }
}
