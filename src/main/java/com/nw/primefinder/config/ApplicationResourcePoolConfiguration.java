package com.nw.primefinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.nw.primefinder.Constants.REQUEST_TASK_EXECUTOR;

@Configuration
public class ApplicationResourcePoolConfiguration {
    @Value("${request.executor.threadpool.coreSize:20}")
    int requestTaskExecutorThreadPoolCoreSize;
    @Value("${request.executor.threadpool.maxSize:50}")
    int requestTaskExecutorThreadPoolMaxSize;
    @Value("${request.executor.threadpool.queueSize:20}")
    int requestTaskExecutorThreadQueueCapacity;

    /**
     * Returns thread pool executor which is referred by CompletableFuture to
     * execute requests.
     *
     * @return ConcurrentTaskExecutor
     */
    @Bean(name = REQUEST_TASK_EXECUTOR)
    public ConcurrentTaskExecutor getConcurrentRequestTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(requestTaskExecutorThreadPoolCoreSize);
        executor.setMaxPoolSize(requestTaskExecutorThreadPoolMaxSize);
        executor.setThreadNamePrefix(REQUEST_TASK_EXECUTOR);
        executor.setQueueCapacity(requestTaskExecutorThreadQueueCapacity);
        executor.initialize();
        return new ConcurrentTaskExecutor(executor);
    }
}
