package cl.company.omypay.helpcenter.configuration.application;

import cl.company.omypay.helpcenter.configuration.domain.Toggle;
import cl.company.omypay.helpcenter.configuration.domain.ToggleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConfigurationServiceTest {

    private ToggleRepository toggleRepository;
    private ConfigurationService configurationService;

    @BeforeEach
    void setUp() {
        toggleRepository = mock(ToggleRepository.class);
        configurationService = new ConfigurationService(toggleRepository);
    }

    @Test
    void shouldFetchAllTogglesFromRepository() {
        List<Toggle> expectedToggles = stubToggles();
        when(toggleRepository.findAllToggles()).thenReturn(expectedToggles);
        List<Toggle> toggles = configurationService.getConfigurationToggles();

        verify(toggleRepository).findAllToggles();
        assertEquals(expectedToggles.size(), toggles.size());
        assertEquals(expectedToggles.get(0), toggles.get(0));
    }

    private List<Toggle> stubToggles() {
        return Collections.singletonList(new Toggle("A_TOGGLE_KEY", true));
    }
}