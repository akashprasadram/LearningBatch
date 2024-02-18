package com.bank.app.batch.config.job_defination.dataload.step;

import com.bank.app.domain.common.error.exceptions.RelationshipLoadError;
import com.bank.app.domain.runtime.entities.Customer;
import com.bank.app.domain.runtime.entities.Relationship;
import com.bank.app.domain.staging.entities.StgCustomer;
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
public class RelationshipETLStep {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipETLStep.class);
    private final ItemReader<StgRelationship> reader;
    private final ItemProcessor<StgRelationship, Relationship> processor;
    private final ItemWriter<Relationship> writer;


    private final JpaTransactionManager runtimeTransactionManager;


    public RelationshipETLStep(@Qualifier("relationshipReader") ItemReader<StgRelationship> reader
            , @Qualifier("relationshipTransformationProcessor") ItemProcessor<StgRelationship, Relationship> processor
            , @Qualifier("relationshipWriter") ItemWriter<Relationship> writer
            , @Qualifier("runtimeTransactionManager") JpaTransactionManager runtimeTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.runtimeTransactionManager = runtimeTransactionManager;
    }

    @Bean(name="relationshipETLStepBean")
    public Step getRelationshipETLStep(JobRepository jobRepository){
        LOGGER.info("Inside getRelationshipETLStep()");
        return new StepBuilder("relationshipETLStep",jobRepository)
                .<StgRelationship,Relationship>chunk(5,runtimeTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(RelationshipLoadError.class)
                .skipLimit(10)
                .retryLimit(3)
                .retry(RelationshipLoadError.class)
                .build();

    }
}
