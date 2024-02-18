package com.bank.app.batch.config.job_defination.data_validation.step;

import com.bank.app.domain.common.error.exceptions.StgCustomerValidationError;
import com.bank.app.domain.staging.entities.StgCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
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
    private final ItemReader<StgCustomer> reader;
    private final ItemProcessor<StgCustomer,StgCustomer> processor;
    private final ItemWriter<StgCustomer> writer;

    private final SkipListener<StgCustomer,StgCustomer> skipListener;


    private final JpaTransactionManager stagingTransactionManager;


    public CustomerValidatorStep(@Qualifier("stgCustomerReader") ItemReader<StgCustomer> reader
            , @Qualifier("stgCustomerValidationProcessor") ItemProcessor<StgCustomer, StgCustomer> processor
            , @Qualifier("stgCustomerWriter") ItemWriter<StgCustomer> writer
            , @Qualifier("customerValidationSkipListener") SkipListener<StgCustomer, StgCustomer> skipListener
            , @Qualifier("stagingTransactionManager") JpaTransactionManager stagingTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.skipListener = skipListener;
        this.stagingTransactionManager = stagingTransactionManager;
    }

    @Bean(name="stgCustomerValidatorStep")
    public Step getCustomerValidatorStep(JobRepository jobRepository){
        LOGGER.info("Inside getCustomerValidatorStep()");
        return new StepBuilder("CustomerValidatorStep",jobRepository)
                .<StgCustomer,StgCustomer>chunk(5,stagingTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(StgCustomerValidationError.class)
                .skipLimit(Integer.MAX_VALUE)
                .listener(skipListener)
                .build();

    }
}
