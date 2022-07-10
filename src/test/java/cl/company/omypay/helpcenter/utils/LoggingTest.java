package cl.company.omypay.helpcenter.utils;

import cl.company.omypay.helpcenter.shared.logging.Logger;
import cl.company.omypay.helpcenter.shared.logging.LoggingHandler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class LoggingTest {
    protected LoggingHandler infoLoggerHandler;
    protected LoggingHandler warnLoggerHandler;
    protected LoggingHandler errorLoggerHandler;
    protected Logger logger;

    public void setup() throws Exception {
        infoLoggerHandler = mock(LoggingHandler.class);
        warnLoggerHandler = mock(LoggingHandler.class);
        errorLoggerHandler = mock(LoggingHandler.class);
        logger = mock(Logger.class);
        stubLogger();
    }

    private void stubLogger() {
        stubLoggerHandler(infoLoggerHandler);
        stubLoggerHandler(warnLoggerHandler);
        stubLoggerHandler(errorLoggerHandler);

        when(logger.info()).thenReturn(infoLoggerHandler);
        when(logger.warn()).thenReturn(warnLoggerHandler);
        when(logger.error()).thenReturn(errorLoggerHandler);
    }

    protected void stubLoggerHandler(LoggingHandler loggerHandler) {
        when(loggerHandler.setMethod(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setDescription(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setPath(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setServiceStatusCode(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setServiceExecutionTime(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setSystemInError(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setServiceRequestUri(anyString())).thenReturn(loggerHandler);
        when(loggerHandler.setException(any())).thenReturn(loggerHandler);
        when(loggerHandler.setMessageParameter(anyString(), anyString())).thenReturn(loggerHandler);
    }
}
