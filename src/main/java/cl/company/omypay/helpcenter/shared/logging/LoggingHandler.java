package cl.company.omypay.helpcenter.shared.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class LoggingHandler {

    private static final String EXTERNAL_SERVICE_RESPONSE_KEY = "externalServiceResponse";
    private static final String STACK_TRACE_KEY = "stackTrace";
    private static final String LOG_SEPARATOR = " ";
    private final Map<String, String> attributes = new HashMap<>();

    public abstract void log();

    public abstract void log(final String message);

    public LoggingHandler setPath(String contextPath) {
        setMessageParameter("path", contextPath);
        return this;
    }

    public LoggingHandler setServiceRequestUri(final String serviceRequestUri) {
        setMessageParameter("serviceRequestURI", serviceRequestUri);
        return this;
    }

    public LoggingHandler setServiceStatusCode(String serviceStatusCode) {
        setMessageParameter("serviceStatusCode", serviceStatusCode);
        return this;
    }

    public LoggingHandler setServiceExecutionTime(String executionTime) {
        setMessageParameter("serviceExecutionTime", executionTime);
        return this;
    }

    public LoggingHandler setMethod(final String method) {
        setMessageParameter("method", method);
        return this;
    }

    public LoggingHandler setDescription(final String description) {
        setMessageParameter("description", description);
        return this;
    }

    public LoggingHandler setSystemInError(final String systemInError) {
        setMessageParameter("systemInError", systemInError);
        return this;
    }

    public LoggingHandler setException(final Throwable exception) {
        if (exception == null) {
            return this;
        }
        String stackTrace = getStackTraceAsString(exception);
        setMessageParameter("stackTrace", stackTrace);
        setSystemInError(exception.getClass().getSimpleName());
        return this;
    }

    public LoggingHandler setMessageParameter(final String key, final String value) {
        this.attributes.put(key, value);
        return this;
    }

    private String getStackTraceAsString(Throwable exception) {
        String message = "";
        String resumedStackTrace = "";
        String resumedCause = "";

        if (exception.getMessage() != null) {
            message = exception.getMessage();
        }

        if (exception.getStackTrace().length > 0) {
            resumedStackTrace = " | Details: " + exception.getStackTrace()[0];
        }

        if (exception.getCause() != null && exception.getCause().getStackTrace().length > 0) {
            resumedCause = " | Caused by: " + exception.getCause().getStackTrace()[0];
        }

        return message + resumedStackTrace + resumedCause;
    }

    protected String createMessage() {
        List<Map.Entry<String, String>> sortedEntries = excludeNullElementsAndSortMapByKeys(attributes);
        List<String> keyValueStringList = transformEntryListInFormattedStringList(sortedEntries);

        return String.join(LOG_SEPARATOR, keyValueStringList);
    }

    private String getKeyValueAsString(String key) {
        return String.format("%s=\"%s\"", key, attributes.get(key));
    }

    private List<String> transformEntryListInFormattedStringList(List<Map.Entry<String, String>> entries) {
        return entries.stream()
                .map(stringEntry -> getKeyValueAsString(stringEntry.getKey()))
                .collect(Collectors.toList());
    }

    private List<Map.Entry<String, String>> excludeNullElementsAndSortMapByKeys(Map<String, String> attributesMap) {
        return attributesMap.entrySet().stream().filter(stringEntry -> stringEntry.getValue() != null).sorted((o1, o2) -> {
            if (o2.getKey().equals(EXTERNAL_SERVICE_RESPONSE_KEY) || o2.getKey().equals(STACK_TRACE_KEY)) {
                return -1;
            }

            if (o1.getKey().equals(EXTERNAL_SERVICE_RESPONSE_KEY) || o1.getKey().equals(STACK_TRACE_KEY)) {
                return 1;
            }

            return o1.getKey().compareTo(o2.getKey());
        }).collect(Collectors.toList());
    }

}