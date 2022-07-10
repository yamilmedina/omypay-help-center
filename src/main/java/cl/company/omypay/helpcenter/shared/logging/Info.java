package cl.company.omypay.helpcenter.shared.logging;

import org.slf4j.Logger;

public class Info extends LoggingHandler {

    private org.slf4j.Logger log;

    public Info(final Logger log) {
        this.log = log;
    }

    @Override
    public void log() {
        this.log.info(createMessage());
    }

    @Override
    public void log(final String message) {
        this.log.info(message);
    }

}
