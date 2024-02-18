package com.bank.app.batch.config.job_defination.data_validation.reader;

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
public class StgAccountDataReader {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgAccountDataReader.class);


    private final EntityManagerFactory stagingEntityManagerFactory;

    public StgAccountDataReader(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }


    @Bean(name = "stgAccountReader")
    JpaCursorItemReader<StgAccount> stgAccountReader() {
        LOGGER.info("Inside stgAccountReader()");
        return new JpaCursorItemReaderBuilder<StgAccount>()
                .name("stgAccountReader")
                .entityManagerFactory(stagingEntityManagerFactory)
                .queryString("SELECT acc From StgAccount acc WHERE acc.validationStatus=ValidationStatus.NONE")
                //.queryString("SELECT acc From StgAccount acc")
                .build();
    }
}
