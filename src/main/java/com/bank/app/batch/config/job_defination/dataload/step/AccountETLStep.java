package com.bank.app.batch.config.job_defination.dataload.step;

import com.bank.app.domain.runtime.entities.Account;
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
public class AccountETLStep {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountETLStep.class);
    private final ItemReader<StgAccount> reader;
    private final ItemProcessor<StgAccount, Account> processor;
    private final ItemWriter<Account> writer;


    private final JpaTransactionManager runtimeTransactionManager;


    public AccountETLStep(@Qualifier("accountReader") ItemReader<StgAccount> reader
            , @Qualifier("accountTransformationProcessor") ItemProcessor<StgAccount, Account> processor
            , @Qualifier("accountWriter") ItemWriter<Account> writer
            , @Qualifier("runtimeTransactionManager") JpaTransactionManager runtimeTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.runtimeTransactionManager = runtimeTransactionManager;
    }

    @Bean(name="accountETLStepBean")
    public Step getAccountETLStep(JobRepository jobRepository){
        LOGGER.info("Inside getAccountETLStep()");
        return new StepBuilder("accountETLStep",jobRepository)
                .<StgAccount,Account>chunk(5,runtimeTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }
}
