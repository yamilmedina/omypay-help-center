package cl.company.omypay.helpcenter.configuration.infrastructure.dataprovider;

import cl.company.omypay.helpcenter.configuration.domain.Toggle;
import cl.company.omypay.helpcenter.configuration.infrastructure.config.FeatureToggle;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.togglz.junit5.AllEnabled;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class FeatureToggleRepositoryIntegrationTest {

    private FeatureToggleRepository featureToggleRepository;

    @BeforeEach
    void setUp() {
        featureToggleRepository = new FeatureToggleRepository();

    }

    // @
    @AllEnabled(FeatureToggle.class)
    // @Test
    @Disabled("to fix with testcontainers")
    void shouldFetchAndMapAllValuesOfTogglesEnumAllTrue() {
        List<Toggle> toggles = featureToggleRepository.findAllToggles();

        toggles.forEach(it -> assertEquals(true, it.getValue()));
    }

}