package com.darcy.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.api.config.annotation.NacosValue;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	@NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
	private boolean useLocalCache;


	@GetMapping("/localCache")
	public boolean getGroup() {
		return useLocalCache;
	}
}
