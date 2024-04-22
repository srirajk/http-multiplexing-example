package org.example.httpmultiplexingregular.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Configuration
public class ExecutorPoolConfig {

    @Value("${executor.corePoolSize}")
    private int corePoolSize = 10;
    @Value("${executor.maxPoolSize}")
    private int maxPoolSize = 10;
    @Value("${executor.queueCapacity}")
    private int queueCapacity = 10000;

    @Bean("customExecutorService")
    public ExecutorService customExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("custom-executor-");
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }

}
