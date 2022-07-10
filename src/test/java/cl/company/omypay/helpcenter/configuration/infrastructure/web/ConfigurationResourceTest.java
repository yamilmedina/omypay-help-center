package cl.company.omypay.helpcenter.configuration.infrastructure.web;

import cl.company.omypay.helpcenter.configuration.application.ConfigurationService;
import cl.company.omypay.helpcenter.configuration.domain.Toggle;
import cl.company.omypay.helpcenter.utils.LoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConfigurationResourceTest extends LoggingTest {

    private ConfigurationResource configurationResource;
    private ConfigurationService configurationService;

    @BeforeEach
    public void setUp() throws Exception {
        super.setup();
        configurationService = mock(ConfigurationService.class);
        configurationResource = new ConfigurationResource(configurationService, logger);
    }

    @Test
    void shouldRespondOK200WhenTogglesFetchedWithSuccess() {
        List<Toggle> expectedToggles = stubToggles();
        when(configurationService.getConfigurationToggles()).thenReturn(expectedToggles);
        Response response = configurationResource.getConfigurationToggles();

        verify(configurationService).getConfigurationToggles();
        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldRespondBadRequest400WhenTogglesCouldNOTBeFetched() {
        doThrow(new ValidationException("Validation error")).when(configurationService).getConfigurationToggles();
        Response response = configurationResource.getConfigurationToggles();

        verify(configurationService).getConfigurationToggles();
        assertEquals(400, response.getStatus());
    }

    @Test
    void shouldLogWithParamsWhenABadRequest400IsThrown() {
        ValidationException validationException = new ValidationException("Validation error");
        doThrow(validationException).when(configurationService).getConfigurationToggles();

        configurationResource.getConfigurationToggles();

        verify(configurationService).getConfigurationToggles();
        verify(warnLoggerHandler).setMethod("configuration");
        verify(warnLoggerHandler).setDescription("Validation error");
        verify(warnLoggerHandler).setException(validationException);
        verify(warnLoggerHandler).log();
    }

    @Test
    void shouldRespondServiceUnavailable503WhenTogglesCouldNOTBeFetched() {
        doThrow(new RuntimeException("Server error")).when(configurationService).getConfigurationToggles();

        Response response = configurationResource.getConfigurationToggles();

        verify(configurationService).getConfigurationToggles();
        assertEquals(503, response.getStatus());
    }

    @Test
    void shouldLogWithParamsWhenAServiceUnavailable503IsThrown() {
        RuntimeException runtimeException = new RuntimeException("Server error");
        doThrow(runtimeException).when(configurationService).getConfigurationToggles();

        configurationResource.getConfigurationToggles();

        verify(configurationService).getConfigurationToggles();
        verify(errorLoggerHandler).setMethod("configuration");
        verify(errorLoggerHandler).setDescription("Server error");
        verify(errorLoggerHandler).setException(runtimeException);
        verify(errorLoggerHandler).log();
    }

    private List<Toggle> stubToggles() {
        return Collections.singletonList(new Toggle("A_TOGGLE_KEY", true));
    }
}