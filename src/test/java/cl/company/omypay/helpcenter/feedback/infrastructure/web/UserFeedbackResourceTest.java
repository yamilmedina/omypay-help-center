package cl.company.omypay.helpcenter.feedback.infrastructure.web;

import cl.company.omypay.helpcenter.feedback.application.UserFeedbackService;
import cl.company.omypay.helpcenter.utils.LoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserFeedbackResourceTest extends LoggingTest {

    UserFeedbackService userFeedbackService;
    UserFeedbackResource userFeedbackResource;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        userFeedbackService = mock(UserFeedbackService.class);
        userFeedbackResource = new UserFeedbackResource(userFeedbackService, logger);
    }

    @Test
    void shouldRespondOK200WhenIssueSentWithSuccess() {
        doNothing().when(userFeedbackService).sendFeedback(any());

        UserFeedbackRequest request = stubUserFeedbackRequest("name", "name@email.com", "some_feedback");
        Response response = userFeedbackResource.sendFeedback(request);

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldRespondServiceUnavailable503WhenFeedbackCouldNotBeSent() {
        RuntimeException runtimeException = new RuntimeException("Server Error");
        doThrow(runtimeException).when(userFeedbackService).sendFeedback(any());

        UserFeedbackRequest userFeedbackRequest = stubUserFeedbackRequest("name", "name@email.com", "some_feedback");
        Response response = userFeedbackResource.sendFeedback(userFeedbackRequest);

        assertEquals(503, response.getStatus());
        verify(errorLoggerHandler).setMethod("sendFeedback");
        verify(errorLoggerHandler).setDescription("Server Error");
        verify(errorLoggerHandler).setMessageParameter("user", "name@email.com");
        verify(errorLoggerHandler).setException(runtimeException);
        verify(errorLoggerHandler).log();
    }

    @Test
    void shouldRespondBadRequest400WhenArgumentsAreNotOKForFeedbackSent() {
        ValidationException validationException = new ValidationException("Validation Error");
        doThrow(validationException).when(userFeedbackService).sendFeedback(any());

        UserFeedbackRequest userFeedbackRequest = stubUserFeedbackRequest("name", "name@email.com", "");
        Response response = userFeedbackResource.sendFeedback(userFeedbackRequest);

        assertEquals(400, response.getStatus());
        verify(userFeedbackService).sendFeedback(any());
        verify(warnLoggerHandler).setMethod("sendFeedback");
        verify(warnLoggerHandler).setDescription("Validation Error");
        verify(warnLoggerHandler).setMessageParameter("user", "name@email.com");
        verify(warnLoggerHandler).setException(validationException);
        verify(warnLoggerHandler).log();
    }

    private UserFeedbackRequest stubUserFeedbackRequest(String name, String email, String message) {
        return new UserFeedbackRequest("name", "name@email.com", "");
    }
}