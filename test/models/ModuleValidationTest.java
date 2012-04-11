package models;

import org.junit.Test;

import static org.apache.commons.lang.RandomStringUtils.random;
import static test.ModulesAssertions.assertThat;

public class ModuleValidationTest {


    @Test
    public void valid() {
        Module module = new ModuleBuilder().build();
        assertThat(module).isValid();
    }

    @Test
    public void keyIsRequired() {
        Module module = new ModuleBuilder().withKey(null).build();
        assertThat(module).isInvalidBecauseMissingRequired("key");
    }

    /* TODO: continue this test and make key unique..
    @Test
    public void keyMustBeUnique() {
        new ModuleBuilder().withKey("aaa").build().save();

        Module module = new ModuleBuilder().withKey("aaa").build();
        assertThat(module).isInvalidBecause("key", "error.unique");
    }
    */

    @Test
    public void nameIsRequired() {
        Module module = new ModuleBuilder().withName(null).build();
        assertThat(module).isInvalidBecauseMissingRequired("name");
    }

    @Test
    public void summaryIsRequired() {
        Module module = new ModuleBuilder().withSummary(null).build();
        assertThat(module).isInvalidBecauseMissingRequired("summary");
    }

    @Test
    public void summaryCannotBeMoreThan500Chars() {
        Module module = new ModuleBuilder().withSummary(random(501)).build();
        assertThat(module).isInvalidBecause("summary", "error.maxLength");
    }

    @Test
    public void summaryCanBe500CharsLong() {
        Module module = new ModuleBuilder().withSummary(random(500)).build();
        assertThat(module).isValid();
    }

    @Test
    public void descriptionIsRequired() {
        Module module = new ModuleBuilder().withDescription(null).build();
        assertThat(module).isInvalidBecauseMissingRequired("description");
    }

    @Test
    public void licenseTypeIsRequired() {
        Module module = new ModuleBuilder().withLicenseType(null).build();
        assertThat(module).isInvalidBecauseMissingRequired("licenseType");
    }

}
