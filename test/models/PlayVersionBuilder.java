package models;

import static models.PlayVersion.MajorVersion;
import static models.PlayVersion.MajorVersion.TWO;

public class PlayVersionBuilder {

    private MajorVersion majorVersion = TWO;
    private String name;

    public PlayVersion build(){
        PlayVersion version = new PlayVersion();
        version.majorVersion = majorVersion;
        version.name = name;
        version.documentationUrl = "http://www.somedoc.com";
        return version;
    }

    public PlayVersionBuilder withMajorVersion(MajorVersion one) {
        this.majorVersion = one;
        return this;
    }

    public PlayVersionBuilder withName(String name) {
        this.name = name;
        return this;
    }
}
