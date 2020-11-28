package com.isoftstone.jxyz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MasterDataSourceConfig {
	@Value("${jxyz.datasource.master.driverClassName}")
	private String driverClassName;

	@Value("${jxyz.datasource.master.url}")
	private String url;

	@Value("${jxyz.datasource.master.username}")
	private String username;

	@Value("${jxyz.datasource.master.password}")
	private String password;

	@Value("${jxyz.datasource.master.hikari.connection-timeout}")
	private Long connectionTimeout;
	@Value("${jxyz.datasource.master.hikari.auto-commit}")
	private Boolean autoCommit;
	@Value("${jxyz.datasource.master.hikari.max-lifetime}")
	private Long maxLifetime;
	@Value("${jxyz.datasource.master.hikari.pool-name}")
	private String poolName;
	@Value("${jxyz.datasource.master.hikari.minimum-idle}")
	private int minimumIdle;
	@Value("${jxyz.datasource.master.hikari.connection-test-query}")
	private String connectionTestQuery;
	@Value("${jxyz.datasource.master.hikari.idle-timeout}")
	private Long idleTimeout;
	@Value("${jxyz.datasource.master.hikari.maximum-pool-size}")
	private int maximumPoolSize;

	@Bean(name = "masterDataSource")
	public DataSource masterDataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(driverClassName);
		hikariDataSource.setUsername(username);
		hikariDataSource.setPassword(password);
		hikariDataSource.setJdbcUrl(url);
		
		hikariDataSource.setConnectionTimeout(connectionTimeout);
		hikariDataSource.setAutoCommit(autoCommit);
		hikariDataSource.setMaxLifetime(maxLifetime);
		hikariDataSource.setPoolName(poolName);
		hikariDataSource.setMinimumIdle(minimumIdle);
		
		hikariDataSource.setConnectionTestQuery(connectionTestQuery);
		hikariDataSource.setIdleTimeout(idleTimeout);
		hikariDataSource.setMaximumPoolSize(maximumPoolSize);
		return hikariDataSource;
	}

}
