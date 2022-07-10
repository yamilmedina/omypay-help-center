package cl.company.omypay.helpcenter.shared.adapters.web;

// import cl.company.commons.logging.container.ServiceMetricsContainerFilter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * ServiceMetricsFilter
 */
@Provider
public class ServiceMetricsFilter implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        // context.register(ServiceMetricsContainerFilter.class);
    }

}