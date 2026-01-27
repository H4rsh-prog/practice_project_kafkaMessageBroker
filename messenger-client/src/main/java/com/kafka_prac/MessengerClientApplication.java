package com.kafka_prac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessengerClientApplication {
	@Value("${spring.kafka.consumer.group-id}") public static String consumer_group_id;
	@Value("${spring.kafka.bootstrap-servers}") public static String bootstrap_servers;

	public static void main(String[] args) {
		SpringApplication.run(MessengerClientApplication.class, args);
	}

}
