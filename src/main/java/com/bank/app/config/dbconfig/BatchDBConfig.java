package com.bank.app.config.dbconfig;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.bank.app.repo.batch" }, entityManagerFactoryRef = "prodEntityManagerFactory", transactionManagerRef = "prodTransactionManager")
@EntityScan(basePackages = { "com.bank.app.entities.batch" })
public class BatchDBConfig {

	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.batchdatasource")
	DataSource batchDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	EntityManagerFactory batchEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");

		lem.setDataSource(batchDataSource());
		lem.setPackagesToScan("com.bank.app.entities.batch");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaProperties(hibernateProperties);
		lem.afterPropertiesSet();

		return lem.getObject();
	}

}
