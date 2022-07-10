package cl.company.omypay.helpcenter.feedback.domain;

/**
 * UserFeedbackRepository
 */
public interface UserFeedbackRepository {
    UserFeedback findFeedbackByUserEmail(String email);
}
