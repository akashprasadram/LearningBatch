package com.bank.app.batch.config.job_defination.dataload.job;

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
public class DataLoadJob {
    private static final Logger LOGGER= LoggerFactory.getLogger(DataLoadJob.class);
    private final Step accountETLStep;

    private final Step customerETLStep;

    private final Step relationshipETLStep;

    public DataLoadJob(@Qualifier("accountETLStepBean") Step accountETLStep
            , @Qualifier("customerETLStepBean") Step customerETLStep
            , @Qualifier("relationshipETLStepBean") Step relationshipETLStep) {
        this.accountETLStep = accountETLStep;
        this.customerETLStep = customerETLStep;
        this.relationshipETLStep = relationshipETLStep;
    }


    @Bean(name="dataLoadJobBean")
    Job getDataLoadJob(JobRepository jobRepository){
        LOGGER.info("Inside getDataLoadJob()");
        return new JobBuilder("dataLoadJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(accountETLStep)
                .next(customerETLStep)
                .next(relationshipETLStep)
                .end()
                .build();
    }
}
