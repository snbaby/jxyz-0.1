package com.isoftstone.jxyz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SlaveDataSourceConfig {
	@Value("${jxyz.datasource.slave.driverClassName}")
	private String driverClassName;

	@Value("${jxyz.datasource.slave.url}")
	private String url;

	@Value("${jxyz.datasource.slave.username}")
	private String username;

	@Value("${jxyz.datasource.slave.password}")
	private String password;

	@Value("${jxyz.datasource.slave.hikari.connection-timeout}")
	private Long connectionTimeout;
	@Value("${jxyz.datasource.slave.hikari.auto-commit}")
	private Boolean autoCommit;
	@Value("${jxyz.datasource.slave.hikari.max-lifetime}")
	private Long maxLifetime;
	@Value("${jxyz.datasource.slave.hikari.pool-name}")
	private String poolName;
	@Value("${jxyz.datasource.slave.hikari.minimum-idle}")
	private int minimumIdle;
	@Value("${jxyz.datasource.slave.hikari.connection-test-query}")
	private String connectionTestQuery;
	@Value("${jxyz.datasource.slave.hikari.idle-timeout}")
	private Long idleTimeout;
	@Value("${jxyz.datasource.slave.hikari.maximum-pool-size}")
	private int maximumPoolSize;

	@Bean(name = "slaveDataSource")
	public DataSource slaveDataSource() {
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
