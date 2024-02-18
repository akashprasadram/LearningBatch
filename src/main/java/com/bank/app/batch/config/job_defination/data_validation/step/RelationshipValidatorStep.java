package com.bank.app.batch.config.job_defination.data_validation.step;

import com.bank.app.domain.common.error.exceptions.StgCustomerValidationError;
import com.bank.app.domain.staging.entities.StgRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidatorStep {
    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipValidatorStep.class);
    private final ItemReader<StgRelationship> reader;
    private final ItemProcessor<StgRelationship,StgRelationship> processor;
    private final ItemWriter<StgRelationship> writer;

    private final JpaTransactionManager stagingTransactionManager;


    public RelationshipValidatorStep(@Qualifier("stgRelationshipReader") ItemReader<StgRelationship> reader
            , @Qualifier("stgRelationshipValidationProcessor") ItemProcessor<StgRelationship, StgRelationship> processor
            , @Qualifier("stgRelationshipWriter") ItemWriter<StgRelationship> writer
            , @Qualifier("stagingTransactionManager") JpaTransactionManager stagingTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.stagingTransactionManager = stagingTransactionManager;
    }

    @Bean(name="stgRelationshipValidatorStep")
    public Step getRelationshipValidatorStep(JobRepository jobRepository){
        LOGGER.info("Inside getRelationshipValidatorStep()");
        return new StepBuilder("RelationshipValidatorStep",jobRepository)
                .<StgRelationship,StgRelationship>chunk(5,stagingTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(StgCustomerValidationError.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();

    }
}
