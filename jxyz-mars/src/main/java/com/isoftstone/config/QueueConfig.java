package com.isoftstone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
public class QueueConfig {
	@Value("${jxyz.rabbitmq.exchange}")
	private String exchange;

	@Value("${jxyz.rabbitmq.err-exchange}")
	private String errExchange;

	@Value("${jxyz.rabbitmq.queue}")
	private String queue;

	@Value("${jxyz.rabbitmq.err-queue}")
	private String errQueue;

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(exchange);
	}

	@Bean
	FanoutExchange errExchange() {
		return new FanoutExchange(errExchange);
	}

	@Bean
	public Queue errQueue() {
		return new Queue(errQueue);
	}

	@Bean
	public Queue queue() {
		return new Queue(queue);
	}

	@Bean
	Binding bindingExchange(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	Binding bindingErrExchange(Queue errQueue, FanoutExchange errExchange) {
		return BindingBuilder.bind(errQueue).to(errExchange);
	}
}
