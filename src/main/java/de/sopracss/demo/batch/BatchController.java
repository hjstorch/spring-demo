package de.sopracss.demo.batch;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BatchController {

    private final JobOperator jobOperator;

    public BatchController(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    @GetMapping(path = "/batch/jobs")
    public Set<String> getConfiguredJobs() {
        return this.jobOperator.getJobNames();
    }
}
