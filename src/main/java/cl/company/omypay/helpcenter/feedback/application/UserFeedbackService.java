package cl.company.omypay.helpcenter.feedback.application;

import cl.company.omypay.helpcenter.shared.adapters.mailer.EmailService;
import cl.company.omypay.helpcenter.shared.vo.EmailMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

/**
 * UserFeedbackService
 */
@ApplicationScoped
public class UserFeedbackService {

    private final EmailService emailService;
    private final static String SUBJECT_TEMPLATE = "[Sugerencia] [%s]";
    private final static String BODY_TEMPLATE = "Nombre: %s \n" +
            "Email: %s \n" +
            "Texto: %s \n";

    @Inject
    public UserFeedbackService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendFeedback(@Valid UserFeedbackCreateData userFeedbackCreateData) {
        var emailMessage = EmailMessage.of(
                userFeedbackCreateData.getEmail(),
                String.format(SUBJECT_TEMPLATE, userFeedbackCreateData.getEmail()),
                String.format(BODY_TEMPLATE,
                        userFeedbackCreateData.getName(),
                        userFeedbackCreateData.getEmail(),
                        userFeedbackCreateData.getFeedback()));

        emailService.sendEmail(emailMessage);
    }

}
