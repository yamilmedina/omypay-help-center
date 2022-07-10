package cl.company.omypay.helpcenter.issue.infrastructure.web;

/**
 * UserIssueRequest
 */
public class UserIssueRequest {

    public String name;
    public String email;
    public String message;
    public String category;

    public UserIssueRequest() {
    }

    public UserIssueRequest(String name, String email, String message, String category) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.category = category;
    }

}
