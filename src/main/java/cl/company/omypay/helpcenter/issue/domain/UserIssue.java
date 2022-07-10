package cl.company.omypay.helpcenter.issue.domain;

/**
 * UserIssue
 */
public class UserIssue {

    private final User user;
    private final String issue;
    private final IssueType issueType;

    public UserIssue(User user, String issue, IssueType issueType) {
        this.user = user;
        this.issue = issue;
        this.issueType = issueType;
    }

    public User getUser() {
        return user;
    }

    public String getIssue() {
        return issue;
    }

    public IssueType getIssueType() {
        return issueType;
    }
}
