package cl.company.omypay.helpcenter.shared.adapters.mailer;

import cl.company.omypay.helpcenter.shared.logging.Logger;
import cl.company.omypay.helpcenter.shared.vo.EmailMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * EmailService
 */
@ApplicationScoped
public class EmailService {

    private final SesClient sesClient;
    private final Executor executor;
    private final String destinationMail;
    private final String fromMail;
    private final Logger logger;

    @Inject
    public EmailService(SesClient sesClient,
                        Executor executor,
                        @ConfigProperty(name = "company.omypay.mailer.to") String destinationMail,
                        @ConfigProperty(name = "company.omypay.mailer.from") String fromMail,
                        Logger logger) {
        this.sesClient = sesClient;
        this.executor = executor;
        this.destinationMail = destinationMail;
        this.fromMail = fromMail;
        this.logger = logger;
    }

    public void sendEmail(EmailMessage emailMessage) {
        CompletableFuture.supplyAsync(() -> sendEmailSupplier(emailMessage), executor)
                .orTimeout(30, TimeUnit.SECONDS)
                .thenAcceptAsync(emailResponse -> logSuccess(emailMessage.getFrom(), emailResponse))
                .exceptionally(ex -> {
                    logFailure(emailMessage.getFrom(), ex);
                    return null;
                });
    }

    private SendEmailResponse sendEmailSupplier(EmailMessage emailMessage) {
        var emailRequest = SendEmailRequest.builder()
                .source(fromMail)
                .destination(destination -> destination.toAddresses(destinationMail))
                .message(message -> {
                    message.subject(subject -> subject.data(emailMessage.getSubject()));
                    message.body(body -> body.text(content -> content.data(emailMessage.getContent())));
                })
                .replyToAddresses(emailMessage.getFrom())
                .build();
        return sesClient.sendEmail(emailRequest);
    }

    private void logSuccess(String user, SendEmailResponse emailResponse) {
        logger.info()
                .setMethod("sendEmail")
                .setDescription("Email sent successfully")
                .setMessageParameter("user", user)
                .setMessageParameter("EmailId", emailResponse.messageId())
                .log();
    }

    private void logFailure(String user, Throwable exception) {
        logger.warn()
                .setMethod("sendEmail")
                .setMessageParameter("user", user)
                .setDescription(exception.getMessage())
                .setException(exception)
                .log();
    }

}
