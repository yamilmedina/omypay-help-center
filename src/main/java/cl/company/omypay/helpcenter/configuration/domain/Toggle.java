package cl.company.omypay.helpcenter.configuration.domain;

/**
 * Toggle
 */
public class Toggle {

    private final String key;
    private final Boolean value;

    public Toggle(String key, Boolean value) {
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
