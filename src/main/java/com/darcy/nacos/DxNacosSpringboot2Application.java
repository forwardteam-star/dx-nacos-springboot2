package com.darcy.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

import static com.darcy.nacos.DxNacosSpringboot2Application.DATA_ID;

@SpringBootApplication
@NacosPropertySource(dataId = DATA_ID, autoRefreshed = true)
@EnableScheduling
public class DxNacosSpringboot2Application {

    public static final String DATA_ID = "example";

    public static void main(String[] args) {
        SpringApplication.run(DxNacosSpringboot2Application.class, args);
    }

}
