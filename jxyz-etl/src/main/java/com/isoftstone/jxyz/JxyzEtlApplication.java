package com.isoftstone.jxyz;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.github.drinkjava2.jtransactions.spring.SpringTxConnectionManager;


/**
 * async异步实现方法只能放在service或component类中，
 * 不能放在controller里，会再代理中找不到报404
 */
@SpringBootApplication
@EnableScheduling
public class JxyzEtlApplication {

    @Autowired
    DataSource ds;

    public static void main(String[] args) {
        SpringApplication.run(JxyzEtlApplication.class, args);
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
