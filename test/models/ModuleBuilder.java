package models;


public class ModuleBuilder {

    private String name = "module name";
    private String key = " moduleKey";
    private String summary = "module summmary";
    private String description = "module description";
    private String licenseType = "license type";
    private String organisation = "organisation";

    public Module build() {
        Module module = new Module();
        module.name = name;
        module.key = key;
        module.summary = summary;
        module.description = description;
        module.licenseType = licenseType;
        module.organisation = organisation;
        return module;
    }

    public ModuleBuilder withKey(String key) {
        this.key = key;
        return this;
    }

    public ModuleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ModuleBuilder withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ModuleBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ModuleBuilder withLicenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }
}
