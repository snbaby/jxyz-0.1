package com.isoftstone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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

	@Bean
	FanoutExchange exchange(@Value("${jxyz.rabbitmq.exchange}") String exchange) {
		return new FanoutExchange(exchange);
	}

	@Bean
	FanoutExchange deadExchange(@Value("${jxyz.rabbitmq.dead-exchange}") String deadExchange) {
		return new FanoutExchange(deadExchange);
	}

	@Bean
	public Queue deadQueue(@Value("${jxyz.rabbitmq.dead-queue}") String deadQueue) {
		return new Queue(deadQueue);
	}

	@Bean
	public Queue queue(@Value("${jxyz.rabbitmq.queue}") String queue,
			@Value("${jxyz.rabbitmq.dead-exchange}") String deadExchange) {
		return QueueBuilder.durable(queue).withArgument("x-dead-letter-exchange", deadExchange).build();
	}

	@Bean
	Binding bindingExchange(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	Binding bindingDeadExchange(Queue deadQueue, FanoutExchange deadExchange) {
		return BindingBuilder.bind(deadQueue).to(deadExchange);
	}
}
