package cl.company.omypay.helpcenter.shared.logging;

import org.slf4j.Logger;
public class Debug extends LoggingHandler {

    private org.slf4j.Logger log;

    public Debug(final Logger log) {
        this.log = log;
    }

    @Override
    public void log() {
        this.log.debug(createMessage());
    }

    @Override
    public void log(final String message) {
        this.log.debug(message);
    }

}