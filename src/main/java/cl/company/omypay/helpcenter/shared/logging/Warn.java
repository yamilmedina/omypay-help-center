package cl.company.omypay.helpcenter.shared.logging;

import org.slf4j.Logger;

public class Warn extends LoggingHandler {

    private org.slf4j.Logger log;

    public Warn(final Logger log) {
        this.log = log;
    }

    @Override
    public void log() {
        this.log.warn(createMessage());
    }

    @Override
    public void log(final String message) {
        this.log.warn(message);
    }

}