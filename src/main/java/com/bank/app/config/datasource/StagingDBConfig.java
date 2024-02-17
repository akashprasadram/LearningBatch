package com.bank.app.config.datasource;

import java.util.Properties;
import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.bank.app.domain.staging.repository", entityManagerFactoryRef = "stagingEntityManagerFactory", transactionManagerRef = "stagingTransactionManager")
@EntityScan(basePackages = { "com.bank.app.domain.staging.entities" })
public class StagingDBConfig {

	@Bean(name = "stagingDataSource")
	@ConfigurationProperties(prefix = "spring.stagingdatasource")
	DataSource stagingDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "stagingEntityManagerFactory")
	EntityManagerFactory stagingEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");

		lem.setDataSource(stagingDataSource());
		lem.setPackagesToScan("com.bank.app.domain.staging.entities");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaProperties(hibernateProperties);
		lem.afterPropertiesSet();

		return lem.getObject();

	}

	@Bean(name = "stagingTransactionManager")
	JpaTransactionManager stagingTransactionManager() {
		return new JpaTransactionManager(stagingEntityManagerFactory());
	}

}
