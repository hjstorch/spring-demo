package de.sopracss.demo.batch;


import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecutionException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledJobsRunner {

    private final Job taxJob;
    private final JobOperator operator;

    public ScheduledJobsRunner(Job taxJob, JobOperator operator) {
        this.taxJob = taxJob;
        this.operator = operator;
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void runAtNight() throws JobExecutionException {
        operator.start(taxJob, new JobParameters());
    }
}
