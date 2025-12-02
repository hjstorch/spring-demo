package de.sopracss.demo.batch.tax;

import de.sopracss.demo.persistence.entity.TaxEntity;
import de.sopracss.demo.persistence.repository.TaxRepository;
import jakarta.persistence.*;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
public class TaxBatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;

    public TaxBatchConfig(EntityManagerFactory entityManagerFactory, JobRepository jobRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job taxJob(JobRepository jobRepository, Step taxStep) {
        return new JobBuilder("taxJob", jobRepository)
                .start(taxStep)
                // .next(nextStep)
                .build();
    }

    @Bean
    public Step taxStep(TaxProcessor taxProcessor, JobRepository jobRepository,
                        PlatformTransactionManager transactionManager,
                        TaskExecutor simpleAsyncTaskExecutor,
                        ItemReader<TaxEntity> taxReader, ItemWriter<TaxEntity> taxWriter) {
        return new StepBuilder("tax", jobRepository)
                .<TaxEntity, TaxEntity>chunk(1)
                .transactionManager(transactionManager)
                .reader(taxReader)
                .processor(taxProcessor)
                .writer(taxWriter)
                .taskExecutor((AsyncTaskExecutor) simpleAsyncTaskExecutor)
                .build()
        ;
    }

    @Bean
    public ItemReader<TaxEntity> taxReader() {
        return new JpaPagingItemReaderBuilder<TaxEntity>()
                .name("taxreader")
                .entityManagerFactory(this.entityManagerFactory)
                .queryString("SELECT t FROM TaxEntity t")
                .pageSize(1)
                .build();
    }

    @Bean
    public ItemWriter<TaxEntity> taxWriter() {
        return new JpaItemWriterBuilder<TaxEntity>()
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }


    // Alternative reader and writer using the JPARepository directly
    // this does not need an EntityManager
    @Bean
    public ItemReader<TaxEntity> taxRepositoryReader(TaxRepository taxRepository) {
        return new RepositoryItemReaderBuilder<TaxEntity>()
                .name("taxrepositoryreader")
                .repository(taxRepository)
                .methodName("findAll")
                .pageSize(1)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemWriter<TaxEntity> taxRepositoryWriter(TaxRepository taxRepository) {
        return new RepositoryItemWriterBuilder<TaxEntity>()
                .repository(taxRepository)
                .build();
    }

//    @Bean
//    public Job sampleJob(JobRepository jobRepository) {
//        Step step1 = createStep("1");
//        Step step2 = createStep("2");
//        Step step3 = createStep("3");
//        Step step4 = createStep("4");
//        return new JobBuilder("sampleJob", jobRepository)
//                .start(step1)
//                .on("*").to(step2)
//                .from(step1).on("FAILED").to(step3)
//                .next(step4)
//                .end()
//                .build();
//    }
//
//    private Step createStep(String name) {
//        return new StepBuilder("step"+name, jobRepository)
//                .chunk(1, transactionManager).build();
//    }
}
