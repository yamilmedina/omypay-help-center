package cl.company.omypay.helpcenter.shared.adapters.mailer;

import cl.company.omypay.helpcenter.shared.vo.EmailMessage;
import cl.company.omypay.helpcenter.utils.LoggingTest;
import cl.company.omypay.helpcenter.utils.SynchronousTestExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.util.concurrent.Executor;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest extends LoggingTest {

    SesClient sesClient;
    EmailService emailService;
    Executor synchronousTestExecutor = new SynchronousTestExecutor();
    private final static String sendTo = "omypay@company.cl";
    private final static String sendFrom = "noreply@company.cl";

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        sesClient = mock(SesClient.class);
        emailService = new EmailService(sesClient, synchronousTestExecutor, sendTo, sendFrom, logger);
    }

    @Test
    void shouldSendTheEmailUsingMailerClient() {
        emailService.sendEmail(mock(EmailMessage.class));

        verify(sesClient).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void shouldSetupReplyToUsingFromArgument() {
        emailService.sendEmail(EmailMessage.of("test@email.com", "some subject", "some content"));

        ArgumentCaptor<SendEmailRequest> mailArgument = ArgumentCaptor.forClass(SendEmailRequest.class);

        verify(sesClient).sendEmail(mailArgument.capture());

        assertThat(mailArgument.getValue().hasReplyToAddresses(), is(true));
        assertThat(mailArgument.getValue().replyToAddresses(), hasItem("test@email.com"));
    }

    @Test
    void shouldSetSubjectAccordinglyFromEmailMessageParam() {
        emailService.sendEmail(EmailMessage.of("test@email.com", "some subject", "some content"));

        ArgumentCaptor<SendEmailRequest> mailArgument = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(sesClient).sendEmail(mailArgument.capture());

        assertThat(mailArgument.getValue().message().subject().data(), is("some subject"));
    }

    @Test
    void shouldSetMessageContentAccordinglyFromEmailMessageParam() {
        emailService.sendEmail(EmailMessage.of("test@email.com", "some subject", "some content"));

        ArgumentCaptor<SendEmailRequest> mailArgument = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(sesClient).sendEmail(mailArgument.capture());

        assertThat(mailArgument.getValue().message().body().text().data(), is("some content"));
    }

    @Test
    void shouldFromSourceParamAccordingly() {
        emailService.sendEmail(EmailMessage.of("test@email.com", "some subject", "some content"));

        ArgumentCaptor<SendEmailRequest> mailArgument = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(sesClient).sendEmail(mailArgument.capture());

        assertThat(mailArgument.getValue().source(), is("noreply@company.cl"));
    }

}