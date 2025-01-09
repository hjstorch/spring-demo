package de.sopracss.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledJobsRunner {

    private final Job taxJob;
    private final JobLauncher launcher;

    public ScheduledJobsRunner(Job taxJob, JobLauncher launcher) {
        this.taxJob = taxJob;
        this.launcher = launcher;
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void runAtNight() throws JobExecutionException {
        launcher.run(taxJob, new JobParameters());
    }
}
