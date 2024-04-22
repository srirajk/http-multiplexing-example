package org.example.httpmultiplexingregular.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@Configuration
public class ExecutorPoolConfig {

    @Bean("customExecutorService")
    public ExecutorService customExecutorService() {
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("custom-executor-");
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }

}
