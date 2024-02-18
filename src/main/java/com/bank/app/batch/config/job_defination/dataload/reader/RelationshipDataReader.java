package com.bank.app.batch.config.job_defination.dataload.reader;

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
public class RelationshipDataReader {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipDataReader.class);


    private final EntityManagerFactory stagingEntityManagerFactory;

    public RelationshipDataReader(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }


    @Bean(name = "relationshipReader")
    JpaCursorItemReader<StgRelationship> relationshipReader() {
        LOGGER.info("Inside RelationshipDataReader.relationshipReader()");
        return new JpaCursorItemReaderBuilder<StgRelationship>()
                .name("relationshipReader")
                .entityManagerFactory(stagingEntityManagerFactory)
                .queryString("SELECT r FROM StgRelationship r WHERE r.validationStatus=ValidationStatus.PASS")
                .build();
    }
}
