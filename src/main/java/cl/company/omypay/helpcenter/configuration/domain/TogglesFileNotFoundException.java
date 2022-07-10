package cl.company.omypay.helpcenter.configuration.domain;

/**
 * TogglesFileNotFoundException
 */
public class TogglesFileNotFoundException extends RuntimeException {
    public TogglesFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public TogglesFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
