package de.sopracss.demo.batch.tax;

import de.sopracss.demo.persistence.entity.TaxEntity;
import de.sopracss.demo.persistence.repository.TaxRepository;
import jakarta.persistence.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
// not necessary > SpringBoot3/SpringBatch5
// @EnableBatchProcessing
public class TaxBatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;

    public TaxBatchConfig(EntityManagerFactory entityManagerFactory, JobRepository jobRepository, DataSourceTransactionManager transactionManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public TaskExecutor simpleTaskExecutor() {
        return new SimpleAsyncTaskExecutor("tax_batch");
    }

    @Bean
    public Job taxJob(JobRepository jobRepository, Step taxStep) {
        return new JobBuilder("taxJob",jobRepository)
                .start(taxStep)
                // .next(nextStep)
                .build();
    }

    @Bean
    public Step taxStep(TaxProcessor taxProcessor, JobRepository jobRepository, PlatformTransactionManager transactionManager, TaskExecutor simpleTaskExecutor) {
        return new StepBuilder("tax", jobRepository)
                .<TaxEntity, TaxEntity>chunk(1, transactionManager)
                .reader(taxReader())
                .processor(taxProcessor)
                .writer(taxWriter())
                .taskExecutor(simpleTaskExecutor)
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
                .pageSize(1)
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
