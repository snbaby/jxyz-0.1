package com.isoftstone.jxyz.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Service
@FeignClient(url="${jxyz.url}",name = "transaction")
public interface TransactionService {

    @RequestMapping(value = "/http_request",method = RequestMethod.POST)
    int httpRequest(@RequestBody Map map);
}
