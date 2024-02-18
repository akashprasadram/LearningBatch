package com.bank.app.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages =  "com.bank.app.domain.runtime.repository"
, entityManagerFactoryRef = "runtimeEntityManagerFactory"
,transactionManagerRef ="runtimeTransactionManager"

)
@EntityScan(basePackages = {"com.bank.app.domain.runtime.entities"})
public class RuntimeDBConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.runtimedatasource")
	DataSource runtimeDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="runtimeEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean runtimeEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("runtimeDataSource") DataSource dataSource) {
		
		Map<String, String> hibernateProperties = new HashMap<>();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.show_sql", "true");
		 return builder
	                .dataSource(dataSource)
	                .packages("com.bank.app.domain.runtime.entities")
	                .persistenceUnit("runtimePersistenceUnit") // Unique persistence unit name for the production database
	                .properties(hibernateProperties)
	                .build();
	}
	
	@Bean(name="runtimeTransactionManager")
	JpaTransactionManager runtimeTransactionManager( @Qualifier("runtimeEntityManagerFactory") EntityManagerFactory prodEntityManagerFactory) {
		return new JpaTransactionManager(prodEntityManagerFactory);
	}
}
