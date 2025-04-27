package com.pragma.plaza_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlazaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlazaServiceApplication.class, args);
	}

}
