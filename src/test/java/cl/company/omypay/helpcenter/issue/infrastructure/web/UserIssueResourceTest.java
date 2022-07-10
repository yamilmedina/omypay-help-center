package cl.company.omypay.helpcenter.issue.infrastructure.web;

import cl.company.omypay.helpcenter.issue.application.UserIssueService;
import cl.company.omypay.helpcenter.utils.LoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserIssueResourceTest extends LoggingTest {

    UserIssueService userIssueService;
    UserIssueResource userIssueResource;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        userIssueService = mock(UserIssueService.class);
        userIssueResource = new UserIssueResource(userIssueService, logger);
    }

    @Test
    void shouldRespondOK200WhenIssueSentWithSuccess() {
        doNothing().when(userIssueService).sendIssue(any());

        UserIssueRequest request = stubUserIssueRequest();
        Response response = userIssueResource.sendIssue(request);

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldRespondServiceUnavailable503WhenIssueCouldNotBeSent() {
        RuntimeException runtimeException = new RuntimeException("Server error");
        doThrow(runtimeException).when(userIssueService).sendIssue(any());

        UserIssueRequest request = stubUserIssueRequest();
        Response response = userIssueResource.sendIssue(request);

        assertEquals(503, response.getStatus());
        verify(userIssueService).sendIssue(any());
        verify(errorLoggerHandler).setMethod("sendIssue");
        verify(errorLoggerHandler).setDescription("Server error");
        verify(errorLoggerHandler).setException(runtimeException);
        verify(errorLoggerHandler).log();
    }

    @Test
    void shouldRespondBadRequest400WhenArgumentsAreNotOKForIssueSent() {
        ValidationException validationException = new ValidationException("Validation error");
        doThrow(validationException).when(userIssueService).sendIssue(any());

        UserIssueRequest request = stubUserIssueRequest();
        Response response = userIssueResource.sendIssue(request);

        assertEquals(400, response.getStatus());
        verify(userIssueService).sendIssue(any());
        verify(warnLoggerHandler).setMethod("sendIssue");
        verify(warnLoggerHandler).setDescription("Validation error");
        verify(warnLoggerHandler).setException(validationException);
        verify(warnLoggerHandler).log();
    }

    private UserIssueRequest stubUserIssueRequest() {
        return new UserIssueRequest("name", "name@email.com", "some_feedback", "cards");
    }
}