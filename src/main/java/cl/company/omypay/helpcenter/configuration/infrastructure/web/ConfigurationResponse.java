package cl.company.omypay.helpcenter.configuration.infrastructure.web;

/**
 * ConfigurationResponse
 */
public class ConfigurationResponse {

    private final String key;
    private final Boolean value;

    public ConfigurationResponse(String key, Boolean value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Boolean getValue() {
        return value;
    }
}
