package com.bank.app.batch.config.job_defination.data_validation.reader;


import com.bank.app.domain.staging.entities.StgCustomer;
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


    EntityManagerFactory stagingEntityManagerFactory;

    public RelationshipDataReader(@Qualifier("stagingEntityManagerFactory") EntityManagerFactory stagingEntityManagerFactory) {
        this.stagingEntityManagerFactory = stagingEntityManagerFactory;
    }


    @Bean(name = "stgRelationshipReader")
    JpaCursorItemReader<StgRelationship> stgRelationshipReader() {
        LOGGER.info("Inside stgRelationshipReader()");
        return new JpaCursorItemReaderBuilder<StgRelationship>()
                .name("stgRelationshipReader")
                .entityManagerFactory(stagingEntityManagerFactory)
                .queryString("From StgRelationship")
                .build();
    }
}
