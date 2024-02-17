package com.bank.app.api.batch.controller;

import com.bank.app.api.batch.services.JobService;
import com.bank.app.domain.common.error.exceptions.InvalidJobNameException;
import com.bank.app.domain.common.error.exceptions.JobStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job/v1")
public class BatchController {

    private static final Logger LOGGER= LoggerFactory.getLogger(BatchController.class);
    private final JobService jobService;
    private final JobOperator jobOperator;

    public BatchController(JobService jobService, JobOperator jobOperator) {
        this.jobService = jobService;
        this.jobOperator = jobOperator;
    }

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable("jobName") String jobName) throws InvalidJobNameException, JobStartException {
        LOGGER.info("Inside startJob() with Job Name : {}",jobName);
        jobService.startJob(jobName);
        return "Job Started...";
    }

    @GetMapping("/stop/{executionId}")
    public String stopJob(@PathVariable("executionId") long executionId) {
        LOGGER.info("Inside stopJob() with Execution Id : {}",executionId);

        try {
            jobOperator.stop(executionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Job Stopped...";

    }
}
