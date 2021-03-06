package com.isoftstone;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.github.drinkjava2.jtransactions.spring.SpringTxConnectionManager;

@SpringBootApplication
@EnableAsync
public class JxyzAdoEtlApplication {
	@Autowired
    DataSource ds;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		SpringApplication.run(JxyzAdoEtlApplication.class, args);
	}
	
	@Bean
	public DbContext createDefaultDbContext() {
		DbContext ctx = new DbContext(ds);
		ctx.setConnectionManager(SpringTxConnectionManager.instance());
		ctx.setAllowShowSQL(true);
		DbContext.setGlobalDbContext(ctx);// 设定静态全局上下文
		return ctx;
	}
}
