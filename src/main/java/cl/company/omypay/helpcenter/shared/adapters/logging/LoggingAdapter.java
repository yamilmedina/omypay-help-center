package cl.company.omypay.helpcenter.shared.adapters.logging;

import cl.company.omypay.helpcenter.shared.logging.Logger;
import io.quarkus.runtime.Startup;

import javax.enterprise.context.ApplicationScoped;

/**
 * LoggingAdapter
 */
public class LoggingAdapter {

    @ApplicationScoped
    @Startup
    public Logger getCommonsLoggerForApp() {
        return new Logger();
    }
}
