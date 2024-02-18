package com.bank.app.batch.config.job_defination.dataload.reader;

import com.bank.app.domain.staging.entities.StgAccount;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class AccountDataReader {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountDataReader.class);


    private final EntityManagerFactory stagingEntityManagerFactory;

    public AccountDataReader(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }


    @Bean(name = "accountReader")
    JpaCursorItemReader<StgAccount> accountReader() {
        LOGGER.info("Inside AccountDataReader.accountReader()");
        return new JpaCursorItemReaderBuilder<StgAccount>()
                .name("accountReader")
                .entityManagerFactory(stagingEntityManagerFactory)
                .queryString("SELECT s FROM StgAccount s WHERE s.validationStatus=ValidationStatus.PASS")
                .build();
    }
}
