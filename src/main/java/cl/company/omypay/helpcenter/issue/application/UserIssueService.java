package cl.company.omypay.helpcenter.issue.application;

import cl.company.omypay.helpcenter.shared.adapters.mailer.EmailService;
import cl.company.omypay.helpcenter.shared.vo.EmailMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

/**
 * UserIssueService
 */
@ApplicationScoped
public class UserIssueService {

    private final EmailService emailService;
    private final static String SUBJECT_TEMPLATE = "[%s] [%s]";
    private final static String BODY_TEMPLATE = "Nombre: %s \n" +
            "Email: %s \n" +
            "Texto: %s \n";

    @Inject
    public UserIssueService(EmailService emailService) {
        this.emailService = emailService;

    }

    public void sendIssue(@Valid UserIssueCreateData userIssueCreateData) {
        var emailMessage = EmailMessage.of(
                userIssueCreateData.getEmail(),
                String.format(SUBJECT_TEMPLATE, userIssueCreateData.getIssueType(), userIssueCreateData.getEmail()),
                String.format(BODY_TEMPLATE, userIssueCreateData.getName(), userIssueCreateData.getEmail(), userIssueCreateData.getIssue()));

        emailService.sendEmail(emailMessage);
    }

}
