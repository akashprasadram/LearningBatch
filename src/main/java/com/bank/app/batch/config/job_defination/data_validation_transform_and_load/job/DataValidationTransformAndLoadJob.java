package com.bank.app.batch.config.job_defination.data_validation_transform_and_load.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataValidationTransformAndLoadJob {
    private static final Logger LOGGER= LoggerFactory.getLogger(DataValidationTransformAndLoadJob.class);
    private final Step accountETLStep;

    public DataValidationTransformAndLoadJob(@Qualifier("accountETLStepBean") Step accountETLStep) {
        this.accountETLStep = accountETLStep;
    }

    @Bean(name="dataValidationTransformAndLoadJobBean")
    Job getDataValidationTransformAndLoadJob(JobRepository jobRepository){
        LOGGER.info("Inside getDataValidationTransformAndLoadJob()");
        return new JobBuilder("dataValidationTransformAndLoadJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(accountETLStep)
                .end()
                .build();
    }
}
