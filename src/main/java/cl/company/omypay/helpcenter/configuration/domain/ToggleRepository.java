package cl.company.omypay.helpcenter.configuration.domain;

import java.util.List;

/**
 * ToggleRepository
 */
public interface ToggleRepository {
    List<Toggle> findAllToggles();
}
