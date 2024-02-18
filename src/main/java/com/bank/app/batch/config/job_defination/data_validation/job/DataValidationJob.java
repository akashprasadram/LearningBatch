package com.bank.app.batch.config.job_defination.data_validation.job;

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
public class DataValidationJob {
    private static final Logger LOGGER= LoggerFactory.getLogger(DataValidationJob.class);
    private final Step accountValidatorStep;

    private final Step customerValidatorStep;

    public DataValidationJob(@Qualifier("stgAccountValidatorStep") Step accountValidatorStep,
                             @Qualifier("stgCustomerValidatorStep") Step  customerValidatorStep) {
        this.accountValidatorStep = accountValidatorStep;
        this.customerValidatorStep = customerValidatorStep;
    }

    @Bean(name="dataValidationJobBean")
    Job getDataValidationJob(JobRepository jobRepository){
        LOGGER.info("Inside getDataValidationJob()");
        return new JobBuilder("dataValidationJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(accountValidatorStep)
                .next(customerValidatorStep)
                .end()
                .build();
    }
}
