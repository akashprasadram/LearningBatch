package com.bank.app.batch.config.job_defination.data_validation.writer;

import com.bank.app.domain.staging.entities.StgCustomer;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StgCustomerDataWriter {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgCustomerDataWriter.class);

    private final EntityManagerFactory stagingEntityManagerFactory;

    public StgCustomerDataWriter(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }

    @Bean(name="stgCustomerWriter")
    JpaItemWriter<StgCustomer> customerWriter(){
        LOGGER.info("Inside customerWriter()");
        return new JpaItemWriterBuilder<StgCustomer>()
                .entityManagerFactory(stagingEntityManagerFactory)
                .build();

    }
}
