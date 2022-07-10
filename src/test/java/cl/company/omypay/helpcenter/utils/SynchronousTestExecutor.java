package cl.company.omypay.helpcenter.utils;

import java.util.concurrent.Executor;

public class SynchronousTestExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}