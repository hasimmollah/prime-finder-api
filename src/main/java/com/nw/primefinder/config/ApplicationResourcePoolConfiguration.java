package com.nw.primefinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.nw.primefinder.Constants.PRIMES_TASK_EXECUTOR;
import static com.nw.primefinder.Constants.REQUEST_TASK_EXECUTOR;

@Configuration
public class ApplicationResourcePoolConfiguration {
    @Value("${executor.threadpool.request.coreSize:20}")
    int requestTaskExecutorThreadPoolCoreSize;
    @Value("${executor.threadpool.request..maxSize:50}")
    int requestTaskExecutorThreadPoolMaxSize;
    @Value("${executor.threadpool.request..queueSize:20}")
    int requestTaskExecutorThreadQueueCapacity;

    @Value("${executor.threadpool.primes..coreSize:20}")
    int primesTaskExecutorThreadPoolCoreSize;
    @Value("${executor.threadpool.primes.maxSize:50}")
    int primesTaskExecutorThreadPoolMaxSize;
    @Value("${executor.threadpool.primes.queueSize:20}")
    int primesTaskExecutorThreadQueueCapacity;

    /**
     * Returns thread pool executor which is referred by CompletableFuture to
     * execute requests.
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(name = REQUEST_TASK_EXECUTOR)
    public ConcurrentTaskExecutor getRequestTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(requestTaskExecutorThreadPoolCoreSize);
        executor.setMaxPoolSize(requestTaskExecutorThreadPoolMaxSize);
        executor.setThreadNamePrefix(REQUEST_TASK_EXECUTOR);
        executor.setQueueCapacity(requestTaskExecutorThreadQueueCapacity);
        executor.initialize();
        return new ConcurrentTaskExecutor (executor);
    }

    /**
     * Returns thread pool executor which is used to get primes.
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(name = PRIMES_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor getPrimesTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(primesTaskExecutorThreadPoolCoreSize);
        executor.setMaxPoolSize(primesTaskExecutorThreadPoolMaxSize);
        executor.setThreadNamePrefix(PRIMES_TASK_EXECUTOR);
        executor.setQueueCapacity(primesTaskExecutorThreadQueueCapacity);
        executor.initialize();
        return executor;
    }
}
