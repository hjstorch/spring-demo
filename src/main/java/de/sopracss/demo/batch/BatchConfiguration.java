package de.sopracss.demo.batch;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
// @EnableBatchProcessing // overriding autoconfiguration - better use spring.batch.... properties configuration
// @EnableJdbcJobRepository // overriding autoconfiguration - better use spring.batch.jdbc.... properties configuration
// @EnableMongoJobRepository // overriding autoconfiguration - better use spring.batch.mongo.... properties configuration
public class BatchConfiguration {

    @Bean
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @Bean
    public TaskExecutor simpleAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("demo_batch");
    }
}
