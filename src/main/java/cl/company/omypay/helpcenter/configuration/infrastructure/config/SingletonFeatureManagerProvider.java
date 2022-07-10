package cl.company.omypay.helpcenter.configuration.infrastructure.config;

import cl.company.omypay.helpcenter.configuration.domain.TogglesFileNotFoundException;
import io.quarkus.runtime.Startup;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.togglz.core.context.StaticFeatureManagerProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.NoOpUserProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

/**
 * SingletonFeatureManagerProvider
 */
@Startup
@ApplicationScoped
public class SingletonFeatureManagerProvider {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @ConfigProperty(name = "company.omypay.helpcenter.toggles.config.file")
    String featuresPropertyFileLocation;
    FeatureManager featureManager;

    @PostConstruct
    public void init() {
        featureManager = new FeatureManagerBuilder()
                .featureEnum(FeatureToggle.class)
                .stateRepository(getTogglzConfig())
                .userProvider(new NoOpUserProvider())
                .build();

        StaticFeatureManagerProvider.setFeatureManager(featureManager);
    }

    private StateRepository getTogglzConfig() {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(featuresPropertyFileLocation)) {
            var tempFile = File.createTempFile("togglz", null);

            if (resourceAsStream == null) {
                logger.info("Loading togglz file from location? {}",
                        String.format("/deployments/features-%s.properties", System.getenv("QUARKUS_PROFILE")));
                tempFile = new File((String.format("/deployments/features-%s.properties", System.getenv("QUARKUS_PROFILE"))));
                return new FileBasedStateRepository(tempFile);
            }

            FileUtils.copyInputStreamToFile(resourceAsStream, tempFile);
            return new FileBasedStateRepository(tempFile);
        } catch (Exception exception) {
            throw new TogglesFileNotFoundException(
                    String.format("Error while loading toggles configuration file %s", featuresPropertyFileLocation),
                    exception);
        }
    }

}
