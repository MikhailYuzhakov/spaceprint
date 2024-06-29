package ru.yuzhakov.service_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceListApplication.class, args);
	}

}
