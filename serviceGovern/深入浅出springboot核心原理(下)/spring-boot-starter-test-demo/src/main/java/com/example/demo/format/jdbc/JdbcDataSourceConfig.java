package com.example.demo.format.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcDataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix="app.datasource.db1")
	public DataSourceProperties db1DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix="app.datasource.db2")
	public DataSourceProperties db2DataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	public DataSource db1DataSource() {
		return db1DataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean
	public DataSource db2DataSource() {
		return db2DataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean(name="db1JdbcTemplate")
	public JdbcTemplate db1JdbcTemplate() {
		return new JdbcTemplate(db1DataSource());
	}
	
	@Bean(name="db2JdbcTemplate")
	public JdbcTemplate db2JdbcTemplate() {
		return new JdbcTemplate(db2DataSource());
	}
	
}
