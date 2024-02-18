package com.bank.app.batch.config.job_defination.data_validation.step;

import com.bank.app.domain.staging.entities.StgAccount;
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
public class CustomerValidatorStep {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerValidatorStep.class);
    private final ItemReader<StgAccount> reader;
    private final ItemProcessor<StgAccount,StgAccount> processor;
    private final ItemWriter<StgAccount> writer;


    private final JpaTransactionManager stagingTransactionManager;


    public CustomerValidatorStep(@Qualifier("stgCustomerReader") ItemReader<StgAccount> reader
            , @Qualifier("stgCustomerValidationProcess") ItemProcessor<StgAccount, StgAccount> processor
            , @Qualifier("stgCustomerWriter") ItemWriter<StgAccount> writer
            , @Qualifier("stagingTransactionManager") JpaTransactionManager stagingTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.stagingTransactionManager = stagingTransactionManager;
    }

    @Bean(name="stgCustomerValidatorStep")
    public Step getCustomerValidatorStep(JobRepository jobRepository){
        LOGGER.info("Inside getCustomerValidatorStep()");
        return new StepBuilder("CustomerValidatorStep",jobRepository)
                .<StgAccount,StgAccount>chunk(5,stagingTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }
}
