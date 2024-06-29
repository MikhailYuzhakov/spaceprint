package ru.yuzhakov.order_storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderStorageApplication {
	public static void main(String[] args) {

		SpringApplication.run(OrderStorageApplication.class, args);
	}
}
