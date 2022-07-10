package cl.company.omypay.helpcenter.configuration.application;

import cl.company.omypay.helpcenter.configuration.domain.Toggle;
import cl.company.omypay.helpcenter.configuration.domain.ToggleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * ConfigurationService
 */
@ApplicationScoped
public class ConfigurationService {

    private final ToggleRepository toggleRepository;

    @Inject
    public ConfigurationService(ToggleRepository toggleRepository) {
        this.toggleRepository = toggleRepository;
    }

    public List<Toggle> getConfigurationToggles() {
        return toggleRepository.findAllToggles();
    }
}
