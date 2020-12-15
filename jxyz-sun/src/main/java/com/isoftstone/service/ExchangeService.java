package com.isoftstone.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class ExchangeService {
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	@Value("${jxyz.rabbitmq.exchange}")
	private String exchange;
	
	public void exchangeSend(JSONObject messageJsb) {
        this.rabbitTemplate.convertAndSend(exchange,"", messageJsb);
    }
}
