package cl.company.omypay.helpcenter.feedback.domain;

/**
 * UserFeedback
 */
public class UserFeedback {

    private final String feedback;
    private final User user;

    public UserFeedback(String feedback, User user) {
        this.feedback = feedback;
        this.user = user;
    }

    public String getFeedback() {
        return feedback;
    }

    public User getUser() {
        return user;
    }
}
