package com.kafka_prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageBroadcastee1Application {

	public static void main(String[] args) {
		SpringApplication.run(MessageBroadcastee1Application.class, args);
	}

}
