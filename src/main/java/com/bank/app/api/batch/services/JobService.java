package com.bank.app.api.batch.services;

import com.bank.app.domain.common.error.exceptions.InvalidJobNameException;
import com.bank.app.domain.common.error.exceptions.JobStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobService {

    private static final Logger LOGGER= LoggerFactory.getLogger(JobService.class);
    private final JobLauncher jobLauncher;

    private final Job dataValidationTransformAndLoadJob;
    private final Job dataLoadJob;

    public JobService(JobLauncher jobLauncher
            , @Qualifier("dataValidationJobBean") Job dataValidationTransformAndLoadJob
            ,@Qualifier("dataLoadJobBean") Job dataLoadJob) {
        this.jobLauncher = jobLauncher;
        this.dataValidationTransformAndLoadJob = dataValidationTransformAndLoadJob;
        this.dataLoadJob = dataLoadJob;
    }

    @Async
    public void startJob(String jobName) throws InvalidJobNameException, JobStartException {

        jobTriggerHandler("Inside startJob() with Job Name : {}", jobName);

    }
    public void startScheduledJob(String jobName) throws InvalidJobNameException, JobStartException {

        jobTriggerHandler("Inside startScheduledJob() with Job Name : {}", jobName);

    }

    private void jobTriggerHandler(String format, String jobName) throws JobStartException, InvalidJobNameException {
        LOGGER.info(format, jobName);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("currentTime", new Date());

        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        if (jobName.equals("dataValidationTransformAndLoadJob")) {
            try {
                jobLauncher.run(dataValidationTransformAndLoadJob, jobParameters);
            } catch (Exception ex) {
                LOGGER.info("Exception while starting Job {}", ex.getMessage());
                throw new JobStartException("Unable to start the Job with name : " + jobName);

            }
        } else if (jobName.equals("dataLoadJob")) {
            try {
                jobLauncher.run(dataLoadJob, jobParameters);
            } catch (Exception ex) {
                LOGGER.info("Exception while starting Job {}", ex.getMessage());
                throw new JobStartException("Unable to start the Job with name : " + jobName);

            }
        } else {
            throw new InvalidJobNameException("Job not found with name : " + jobName);
        }
    }
}
