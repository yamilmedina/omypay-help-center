package cl.company.omypay.helpcenter.configuration.infrastructure.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

/**
 * FeatureToggle
 */
public enum FeatureToggle implements Feature {

    @EnabledByDefault
    @Label("IN_TRANSITION")
    IN_TRANSITION,

    @EnabledByDefault
    @Label("HELP_CENTER_AVAILABLE")
    HELP_CENTER_AVAILABLE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}
