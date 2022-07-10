package cl.company.omypay.helpcenter.feedback.application;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserFeedbackCreateData
 */
public class UserFeedbackCreateData {

    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;
    @Size(min = 1, max = 1000)
    @NotBlank
    private final String feedback;

    public UserFeedbackCreateData(String email, String name, String feedback) {
        this.email = email;
        this.name = name;
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getFeedback() {
        return feedback;
    }
}
