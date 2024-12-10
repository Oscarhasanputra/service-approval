package com.bit.microservices.service_approval.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.concurrent.DelegatingSecurityContextScheduledExecutorService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer{
	
	private static final String TASK_EXECUTOR_NAME_PREFIX = "AsyncTaskExecutor-";
    private static final String POOLED_TASK_EXECUTOR_NAME_PREFIX = "PooledTaskExecutor-";
    
    @PostConstruct
    public void enableAuthCtxOnSpawnedThreads() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    
//    @Bean
//    @Primary
//    Executor executor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
////        executor.setBeanName(TASK_EXECUTOR_NAME);
//        executor.setCorePoolSize(16);
//        executor.setMaxPoolSize(64);
//        executor.setAllowCoreThreadTimeOut(true);
//        executor.setQueueCapacity(32);
//        executor.setThreadNamePrefix(POOLED_TASK_EXECUTOR_NAME_PREFIX);
//        executor.initialize();
//        
//        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
//    }
    
    @Bean
    @Primary
    Executor executor() {
    	ScheduledExecutorService e = Executors.newSingleThreadScheduledExecutor();
    	return new DelegatingSecurityContextScheduledExecutorService(e);
    }
    
    @Bean
    ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        
        eventMulticaster.setTaskExecutor(getAsyncExecutor());
        
        return eventMulticaster;
    }
}
