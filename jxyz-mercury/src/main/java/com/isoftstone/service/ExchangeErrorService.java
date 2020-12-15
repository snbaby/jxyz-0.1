package com.isoftstone.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class ExchangeErrorService {
	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${jxyz.rabbitmq.err-exchange}")
	private String errExchange;

	public void errExchangeSend(JSONObject messageJsb) {
		this.rabbitTemplate.convertAndSend(errExchange, "", messageJsb);
	}
}
