package com.example.security_basic;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SecurityBasicApplication {


	public static void main(String[] args) {
		SpringApplication.run(SecurityBasicApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapperBean() {
		return new ModelMapper();
	}


}
