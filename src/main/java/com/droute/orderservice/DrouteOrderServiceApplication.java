package com.droute.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.droute.orderservice.feign.client")
public class DrouteOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrouteOrderServiceApplication.class, args);
	}

}
