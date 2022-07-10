package cl.company.omypay.helpcenter.shared.executor;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ExternalTaskExecutorService
 */
public class ExternalTaskExecutorService {

    @ApplicationScoped
    public Executor fixedSizeTaskExecutor() {
        return Executors.newFixedThreadPool(Math.floorDiv(Runtime.getRuntime().availableProcessors(), 2) + 1);
    }

}
