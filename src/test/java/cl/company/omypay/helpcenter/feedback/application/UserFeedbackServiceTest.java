package cl.company.omypay.helpcenter.feedback.application;

import cl.company.omypay.helpcenter.shared.adapters.mailer.EmailService;
import cl.company.omypay.helpcenter.shared.vo.EmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserFeedbackServiceTest {

    UserFeedbackService userFeedbackService;

    EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        userFeedbackService = new UserFeedbackService(emailService);
    }

    @Test
    void shouldSendEmailUsingEmailService() {
        UserFeedbackCreateData data =
                new UserFeedbackCreateData("test@email.com", "test_user", "A nice feedback for you!");
        userFeedbackService.sendFeedback(data);

        verify(emailService).sendEmail(any());
    }


    @Test
    void shouldFormatTheSubjectWithSugerenciaAndEmailAsContent() {
        UserFeedbackCreateData data =
                new UserFeedbackCreateData("test@email.com", "test_user", "A nice feedback for you!");
        userFeedbackService.sendFeedback(data);

        ArgumentCaptor<EmailMessage> emailMessageArgument = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailService).sendEmail(emailMessageArgument.capture());

        assertEquals("[Sugerencia] [test@email.com]", emailMessageArgument.getValue().getSubject());
    }

    @Test
    void shouldFormatTheBodyUsingTheDefinedFormatNameEmailAndText() {
        UserFeedbackCreateData data =
                new UserFeedbackCreateData("test@email.com", "test_user", "A feedback!");
        userFeedbackService.sendFeedback(data);

        ArgumentCaptor<EmailMessage> emailMessageArgument = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailService).sendEmail(emailMessageArgument.capture());

        String expectedBody = "Nombre: test_user \n" +
                "Email: test@email.com \n" +
                "Texto: A feedback! \n";
        assertEquals(expectedBody, emailMessageArgument.getValue().getContent());
    }

}