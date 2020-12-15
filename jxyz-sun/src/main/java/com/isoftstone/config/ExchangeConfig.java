package com.isoftstone.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * rabbitmq exchange 配置
 * 
 * @author jianwang
 *
 */
@Component
public class ExchangeConfig {
	@Value("${jxyz.rabbitmq.exchange}")
	private String exchange;

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(exchange);
	}

}
