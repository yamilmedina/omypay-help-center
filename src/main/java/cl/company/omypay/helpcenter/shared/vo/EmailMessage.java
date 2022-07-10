package cl.company.omypay.helpcenter.shared.vo;

/**
 * EmailMessage
 */
public class EmailMessage {

    private final String from;
    private final String subject;
    private final String content;

    private EmailMessage(String from, String subject, String content) {
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    public static EmailMessage of(String from, String subject, String content) {
        return new EmailMessage(from, subject, content);
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
