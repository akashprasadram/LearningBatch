package com.bank.app.api.batch.services;

import com.bank.app.api.staging.controller.StgAccountController;
import com.bank.app.domain.common.error.exceptions.InvalidJobNameException;
import com.bank.app.domain.common.error.exceptions.JobStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JobService.class);
    private final JobLauncher jobLauncher;

    private final Job dataValidationTransformAndLoadJob;

    public JobService(JobLauncher jobLauncher,@Qualifier("dataValidationTransformAndLoadJobBean") Job dataValidationTransformAndLoadJob) {
        this.jobLauncher = jobLauncher;
        this.dataValidationTransformAndLoadJob = dataValidationTransformAndLoadJob;
    }

    @Async
    public void startJob(String jobName) throws InvalidJobNameException, JobStartException {

        LOGGER.info("Inside startJob() with Job Name : {}",jobName);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("currentTime", new Date());

        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        if (jobName.equals("dataValidationTransformAndLoadJob")) {
            try {
            jobLauncher.run(dataValidationTransformAndLoadJob, jobParameters);
            } catch (Exception ex) {
                LOGGER.info("Exception while starting Job {}", ex.getMessage());
                throw new JobStartException("Unable to start the Job with name : "+jobName);

            }
        }else{
            throw new InvalidJobNameException("Job not found with name : "+jobName);
        }

    }
}
