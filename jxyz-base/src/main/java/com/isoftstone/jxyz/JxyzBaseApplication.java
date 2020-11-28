package com.isoftstone.jxyz;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.github.drinkjava2.jtransactions.spring.SpringTxConnectionManager;

@SpringBootApplication
public class JxyzBaseApplication {
	@Autowired
	@Qualifier("masterDataSource")
	DataSource masterDataSource;

	@Autowired
	@Qualifier("slaveDataSource")
	DataSource slaveDataSource;

	public static void main(String[] args) {
		SpringApplication.run(JxyzBaseApplication.class, args);
	}

	@Bean
	public DbContext createDefaultDbContext() {
		DbContext masterCtx = new DbContext(masterDataSource);
		DbContext slaveCtx = new DbContext(slaveDataSource);
		DbContext[] slaveDbContext = { slaveCtx };
		masterCtx.setSlaves(slaveDbContext);
		masterCtx.setMasterSlaveOption(SqlOption.USE_BOTH);
		masterCtx.setConnectionManager(SpringTxConnectionManager.instance());
		masterCtx.setAllowShowSQL(false);

		DbContext.setGlobalDbContext(masterCtx);// 设定静态全局上下文
		DbContext.setGlobalNextMasterSlaveOption(SqlOption.USE_BOTH);
		return masterCtx;
	}
}