package com.bank.app.batch.config.job_defination.dataload.writer;

import com.bank.app.domain.runtime.entities.Account;
import com.bank.app.domain.runtime.entities.Customer;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataWriter {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerDataWriter.class);

    private final EntityManagerFactory runtimeEntityManagerFactory;

    public CustomerDataWriter(@Qualifier("runtimeEntityManagerFactory") EntityManagerFactory runtimeEntityManagerFactory) {
        this.runtimeEntityManagerFactory = runtimeEntityManagerFactory;
    }

    @Bean(name="customerWriter")
    JpaItemWriter<Customer> customerWriter(){
        LOGGER.info("Inside CustomerDataWriter.customerWriter()");
        return new JpaItemWriterBuilder<Customer>()
                .entityManagerFactory(runtimeEntityManagerFactory)
                .build();

    }
}
