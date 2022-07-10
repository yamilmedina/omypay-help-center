package cl.company.omypay.helpcenter.issue.application;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserIssueCreateData
 */
public class UserIssueCreateData {

    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String name;
    @Size(min = 1, max = 1000)
    @NotBlank
    private final String issue;
    @NotBlank
    private final String issueType;

    private UserIssueCreateData(String email, String name, String issue, String issueType) {
        this.email = email;
        this.name = name;
        this.issue = issue;
        this.issueType = issueType;
    }

    public static UserIssueCreateData of(String email, String name, String issue, String issueType) {
        return new UserIssueCreateData(email, name, issue, mapIssueType(issueType));
    }

    private static String mapIssueType(String issueType) {
        switch (issueType) {
            case "payments":
                return "Problema con Pago";
            case "cards":
                return "Problema con Tarjeta";
            case "transfer":
                return "Problema con Transferencias al extranjero";
            case "qr":
                return "Problema con Cobro QR";
            case "other":
            default:
                return "Otro";
        }
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getIssue() {
        return issue;
    }

    public String getIssueType() {
        return issueType;
    }
}
