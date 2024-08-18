package ru.yuzhakov.services_handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdersHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersHandlerApplication.class, args);
	}

}
