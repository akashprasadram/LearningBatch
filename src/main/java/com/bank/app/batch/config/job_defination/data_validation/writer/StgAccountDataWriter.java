package com.bank.app.batch.config.job_defination.data_validation.writer;

import com.bank.app.domain.staging.entities.StgAccount;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StgAccountDataWriter {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgAccountDataWriter.class);

    private final EntityManagerFactory stagingEntityManagerFactory;

    public StgAccountDataWriter(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }

    @Bean(name="stgAccountWriter")
    JpaItemWriter<StgAccount> accountWriter(){
        LOGGER.info("Inside accountWriter()");
        return new JpaItemWriterBuilder<StgAccount>()
                .entityManagerFactory(stagingEntityManagerFactory)
                .build();

    }
}
