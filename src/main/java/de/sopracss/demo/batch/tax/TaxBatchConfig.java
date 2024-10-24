package de.sopracss.demo.batch.tax;

import de.sopracss.demo.persistence.entity.TaxEntity;
import jakarta.persistence.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TaxBatchConfig {


    private final EntityManagerFactory entityManagerFactory;

    public TaxBatchConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job taxJob(JobRepository jobRepository, Step taxStep) {
        return new JobBuilder("taxJob",jobRepository)
                .start(taxStep)
                // .next(nextStep)
                .build();
    }

    @Bean
    public Step taxStep(TaxProcessor taxProcessor, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("tax", jobRepository)
                .<TaxEntity, TaxEntity>chunk(1, transactionManager)
                .reader(taxReader())
                .processor(taxProcessor)
                .writer(taxWriter())
                .build()
        ;
    }

    @Bean
    public ItemReader<TaxEntity> taxReader() {
        return new JpaCursorItemReaderBuilder<TaxEntity>()
                .name("taxreader")
                .entityManagerFactory(this.entityManagerFactory)
                .queryString("SELECT t FROM TAX t")
                .build();
    }

    @Bean
    public ItemWriter<TaxEntity> taxWriter() {
        return new JpaItemWriterBuilder<TaxEntity>()
                .entityManagerFactory(this.entityManagerFactory)
                .build();
    }
}
