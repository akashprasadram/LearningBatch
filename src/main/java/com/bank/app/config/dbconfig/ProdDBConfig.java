package com.bank.app.config.dbconfig;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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

@Configuration
@EnableJpaRepositories(basePackages =  "com.bank.app.repo.prod" 
, entityManagerFactoryRef = "prodEntityManagerFactory"
,transactionManagerRef ="prodTransactionManager"

)
@EntityScan(basePackages = {"com.bank.app.entities.prod"})
public class ProdDBConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.proddatasource")
	DataSource prodDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean prodEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("prodDataSource") DataSource dataSource) {
		
		Map<String, String> hibernateProperties = new HashMap<>();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.show_sql", "true");
		 return builder
	                .dataSource(dataSource)
	                .packages("com.bank.app.entities.prod")
	                .persistenceUnit("prodPersistenceUnit") // Unique persistence unit name for the production database
	                .properties(hibernateProperties)
	                .build();
	}
	
	@Bean
	JpaTransactionManager prodTransactionManager( @Qualifier("prodEntityManagerFactory") EntityManagerFactory prodEntityManagerFactory) {
		return new JpaTransactionManager(prodEntityManagerFactory);
	}
}
