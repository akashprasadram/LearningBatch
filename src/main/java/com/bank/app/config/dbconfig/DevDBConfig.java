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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.bank.app.repo.dev", entityManagerFactoryRef = "devEntityManagerFactory", transactionManagerRef = "devTransactionManager")
@EntityScan(basePackages = { "com.bank.app.entities.dev" })
public class DevDBConfig {

	@Bean(name = "devDataSource")
	@ConfigurationProperties(prefix = "spring.devdatasource")
	DataSource devDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "devEntityManagerFactory")
	EntityManagerFactory devEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");

		lem.setDataSource(devDataSource());
		lem.setPackagesToScan("com.bank.app.entities.dev");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaProperties(hibernateProperties);
		lem.afterPropertiesSet();

		return lem.getObject();

	}

	@Bean(name = "devTransactionManager")
	JpaTransactionManager devTransactionManager() {
		return new JpaTransactionManager(devEntityManagerFactory());
	}

}
