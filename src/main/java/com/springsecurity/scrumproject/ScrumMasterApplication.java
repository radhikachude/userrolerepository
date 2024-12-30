package com.springsecurity.scrumproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.springsecurity.scrumproject"})
public class ScrumMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumMasterApplication.class, args);
	}

}
