package cl.company.omypay.helpcenter.configuration.infrastructure.web;

import cl.company.omypay.helpcenter.configuration.application.ConfigurationService;
import cl.company.omypay.helpcenter.shared.logging.Logger;
import cl.company.omypay.helpcenter.shared.vo.ApiResponse;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ConfigurationResource
 */
@Path("/api/v1/configuration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationResource {

    private final ConfigurationService configurationService;
    private final Logger logger;

    @Inject
    public ConfigurationResource(ConfigurationService configurationService, Logger logger) {
        this.configurationService = configurationService;
        this.logger = logger;
    }

    @GET
    public Response getConfigurationToggles() {
        try {
            List<ConfigurationResponse> toggles = configurationService.getConfigurationToggles()
                    .parallelStream()
                    .map(it -> new ConfigurationResponse(it.getKey(), it.getValue()))
                    .collect(Collectors.toList());
            return Response.ok().entity(ApiResponse.ok(toggles)).build();
        } catch (ValidationException exception) {
            logger.warn()
                    .setMethod("configuration")
                    .setDescription(exception.getMessage())
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        } catch (Exception exception) {
            logger.error()
                    .setMethod("configuration")
                    .setDescription(exception.getMessage())
                    .setException(exception)
                    .log();
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(ApiResponse.failure(exception.hashCode(), exception.getMessage()))
                    .build();
        }
    }
}
