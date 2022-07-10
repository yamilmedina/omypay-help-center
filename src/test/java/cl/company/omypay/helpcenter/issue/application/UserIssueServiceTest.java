package cl.company.omypay.helpcenter.issue.application;

import cl.company.omypay.helpcenter.shared.adapters.mailer.EmailService;
import cl.company.omypay.helpcenter.shared.vo.EmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserIssueServiceTest {

    UserIssueService userIssueService;
    EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        userIssueService = new UserIssueService(emailService);
    }

    @Test
    void shouldSendIssueUsingMailerClient() {
        userIssueService.sendIssue(mock(UserIssueCreateData.class));

        verify(emailService).sendEmail(any());
    }

    @Test
    void shouldFormatTheSubjectAccordingToTheCategorySelectedAndEmail() {
        UserIssueCreateData createData = UserIssueCreateData.of("user@email.com", "some_user", "some_very_blocking_issue", "cards");
        userIssueService.sendIssue(createData);

        ArgumentCaptor<EmailMessage> emailMessageArgument = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailService).sendEmail(emailMessageArgument.capture());

        String expectedSubject = "[Problema con Tarjeta] [user@email.com]";
        assertEquals(expectedSubject, emailMessageArgument.getValue().getSubject());
    }

    @Test
    void shouldFormatTheBodyUsingTheDefinedFormatNameEmailAndText() {
        UserIssueCreateData createData = UserIssueCreateData.of("user@email.com", "some_user", "some_very_blocking_issue", "cards");
        userIssueService.sendIssue(createData);

        ArgumentCaptor<EmailMessage> emailMessageArgument = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailService).sendEmail(emailMessageArgument.capture());

        String expectedBody = "Nombre: some_user \n" +
                "Email: user@email.com \n" +
                "Texto: some_very_blocking_issue \n";
        assertEquals(expectedBody, emailMessageArgument.getValue().getContent());
    }
}