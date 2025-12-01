package de.sopracss.demo.batch;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;

@RestController
public class BatchController {

    private final JobRegistry jobRegistry;

    public BatchController(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }

    @GetMapping(path = "/batch/jobs")
    public Collection<String> getConfiguredJobs() {
        return this.jobRegistry.getJobNames();
    }
}
