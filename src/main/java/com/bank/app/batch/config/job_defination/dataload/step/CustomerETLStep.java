package com.bank.app.batch.config.job_defination.dataload.step;

import com.bank.app.domain.runtime.entities.Customer;
import com.bank.app.domain.staging.entities.StgCustomer;
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
public class CustomerETLStep {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerETLStep.class);
    private final ItemReader<StgCustomer> reader;
    private final ItemProcessor<StgCustomer, Customer> processor;
    private final ItemWriter<Customer> writer;


    private final JpaTransactionManager runtimeTransactionManager;


    public CustomerETLStep(@Qualifier("customerReader") ItemReader<StgCustomer> reader
            , @Qualifier("customerTransformationProcessor") ItemProcessor<StgCustomer, Customer> processor
            , @Qualifier("customerWriter") ItemWriter<Customer> writer
            , @Qualifier("runtimeTransactionManager") JpaTransactionManager runtimeTransactionManager) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.runtimeTransactionManager = runtimeTransactionManager;
    }

    @Bean(name="customerETLStepBean")
    public Step getCustomerETLStep(JobRepository jobRepository){
        LOGGER.info("Inside getCustomerETLStep()");
        return new StepBuilder("customerETLStep",jobRepository)
                .<StgCustomer,Customer>chunk(5,runtimeTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }
}
