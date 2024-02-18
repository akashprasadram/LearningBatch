package com.bank.app.batch.config.job_defination.data_validation.step;

import com.bank.app.domain.common.error.exceptions.StgAccountValidationError;
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
public class AccountValidatorStep {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountValidatorStep.class);
    private final ItemReader<StgAccount> reader;
    private final ItemProcessor<StgAccount,StgAccount> processor;
    private final ItemWriter<StgAccount> writer;


    private final JpaTransactionManager stagingTransactionManager;


    public AccountValidatorStep(@Qualifier("stgAccountReader") ItemReader<StgAccount> reader
            , @Qualifier("stgAccountValidationProcessor") ItemProcessor<StgAccount, StgAccount> processor
            , @Qualifier("stgAccountWriter") ItemWriter<StgAccount> writer
            , @Qualifier("stagingTransactionManager") JpaTransactionManager stagingTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.stagingTransactionManager = stagingTransactionManager;
    }

    @Bean(name="stgAccountValidatorStep")
    public Step getAccountValidatorStep(JobRepository jobRepository){
        LOGGER.info("Inside getAccountValidatorStep()");
        return new StepBuilder("AccountValidatorStep",jobRepository)
                .<StgAccount,StgAccount>chunk(5,stagingTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(StgAccountValidationError.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();

    }
}
