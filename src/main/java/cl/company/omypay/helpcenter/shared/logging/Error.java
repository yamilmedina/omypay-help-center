package cl.company.omypay.helpcenter.shared.logging;

import org.slf4j.Logger;

public class Error extends LoggingHandler {

    private Logger log;

    public Error(final Logger log) {
        this.log = log;
    }

    @Override
    public void log() {
        this.log.error(createMessage());
    }

    @Override
    public void log(final String message) {
        this.log.error(message);
    }
}