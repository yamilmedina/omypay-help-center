package cl.company.omypay.helpcenter.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class FixtureBuilder {

    private String jsonData;

    public FixtureBuilder() {
        this.jsonData = "";
    }

    public FixtureBuilder useFixture(String location) {
        try (FileInputStream jsonFixtureFile = new FileInputStream(new File("src/test/resources/fixtures/" + location))) {
            this.jsonData = IOUtils.toString(jsonFixtureFile, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public FixtureBuilder withKeyAndValue(String key, String value) {
        this.jsonData = this.jsonData.replaceAll("(\"" + key + "\")\\:.*(\".*\")", "$1:\"" + value + "\"");
        return this;
    }

    public String build() {
        return this.jsonData;
    }
}
