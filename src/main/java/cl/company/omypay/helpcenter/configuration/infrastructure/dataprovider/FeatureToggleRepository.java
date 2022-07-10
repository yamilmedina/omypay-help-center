package cl.company.omypay.helpcenter.configuration.infrastructure.dataprovider;

import cl.company.omypay.helpcenter.configuration.infrastructure.config.FeatureToggle;
import cl.company.omypay.helpcenter.configuration.domain.Toggle;
import cl.company.omypay.helpcenter.configuration.domain.ToggleRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FeatureToggleRepository
 */
@ApplicationScoped
public class FeatureToggleRepository implements ToggleRepository {

    @Override
    public List<Toggle> findAllToggles() {
        return Arrays.stream(FeatureToggle.values())
                .parallel()
                .map(it -> new Toggle(it.name(), it.isActive()))
                .collect(Collectors.toList());
    }
}
