package cl.company.omypay.helpcenter.feedback.infrastructure.web;

/**
 * UserFeedbackRequest
 */
public class UserFeedbackRequest {

    public String name;
    public String email;
    public String message;

    public UserFeedbackRequest() {
    }

    public UserFeedbackRequest(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

}
