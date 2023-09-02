package com.nw.primefinder;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.nw.primefinder.Constants.PRIMES_TASK_EXECUTOR;

public class TestUtil {
     private TestUtil(){}

    public static ThreadPoolTaskExecutor getPrimesTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(70);
        executor.setThreadNamePrefix(PRIMES_TASK_EXECUTOR);
        executor.setQueueCapacity(100);
        executor.initialize();
        return executor;
    }
}
