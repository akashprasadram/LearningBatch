package com.bank.app.batch.config.job_defination.dataload.writer;

import com.bank.app.domain.runtime.entities.Account;
import com.bank.app.domain.runtime.entities.Relationship;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RelationshipDataWriter {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipDataWriter.class);

    private final EntityManagerFactory runtimeEntityManagerFactory;

    public RelationshipDataWriter(@Qualifier("runtimeEntityManagerFactory") EntityManagerFactory runtimeEntityManagerFactory) {
        this.runtimeEntityManagerFactory = runtimeEntityManagerFactory;
    }

    @Bean(name="relationshipWriter")
    JpaItemWriter<Relationship> relationshipWriter(){
        LOGGER.info("Inside RelationshipDataWriter.relationshipWriter()");
        return new JpaItemWriterBuilder<Relationship>()
                .entityManagerFactory(runtimeEntityManagerFactory)
                .build();

    }
}
