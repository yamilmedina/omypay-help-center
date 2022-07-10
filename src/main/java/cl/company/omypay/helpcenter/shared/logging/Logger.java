package cl.company.omypay.helpcenter.shared.logging;

public class Logger {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Logger.class);

    public LoggingHandler debug() {
        return new Debug(log);
    }

    public LoggingHandler info() {
        return new Info(log);
    }

    public LoggingHandler warn() {
        return new Warn(log);
    }

    public LoggingHandler error() {
        return new Error(log);
    }

}

