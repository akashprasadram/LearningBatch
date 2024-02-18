package com.bank.app.batch.config.job_defination.data_validation.reader;


import com.bank.app.domain.staging.entities.StgRelationship;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;




@Component
public class StgRelationshipDataReader {
    private static final Logger LOGGER= LoggerFactory.getLogger(StgRelationshipDataReader.class);


    EntityManagerFactory stagingEntityManagerFactory;

    public StgRelationshipDataReader(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }


    @Bean(name = "stgRelationshipReader")
    JpaCursorItemReader<StgRelationship> stgRelationshipReader() {
        LOGGER.info("Inside stgRelationshipReader()");
        return new JpaCursorItemReaderBuilder<StgRelationship>()
                .name("stgRelationshipReader")
                .entityManagerFactory(stagingEntityManagerFactory)
                .queryString("SELECT r FROM StgRelationship r WHERE r.validationStatus=ValidationStatus.NONE")
                //.queryString("SELECT r FROM StgRelationship r")
                .build();
    }
}
