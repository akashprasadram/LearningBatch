package com.bank.app.batch.config.job_defination.data_validation_transform_and_load.writer;

import com.bank.app.domain.runtime.entities.Account;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AccountDataWriter {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountDataWriter.class);

    EntityManagerFactory runtimeEntityManagerFactory;

    public AccountDataWriter(@Qualifier("runtimeEntityManagerFactory") EntityManagerFactory runtimeEntityManagerFactory) {
        this.runtimeEntityManagerFactory = runtimeEntityManagerFactory;
    }

    @Bean(name="accountWriterBean")
    JpaItemWriter<Account> accountWriter(){
        LOGGER.info("Inside accountWriter()");
        return new JpaItemWriterBuilder<Account>()
                .entityManagerFactory(runtimeEntityManagerFactory)
                .build();

    }
}
