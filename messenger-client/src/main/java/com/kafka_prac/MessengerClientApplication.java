package com.kafka_prac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class MessengerClientApplication {
	public static String consumer_group_id;
	public static String bootstrap_servers;
	
	public static void main(String[] args) {
		System.out.println(consumer_group_id+"  "+bootstrap_servers);
		SpringApplication.run(MessengerClientApplication.class, args);
	}
}
@Configuration
class Properties{
	@Value("${spring.kafka.consumer.group-id}")
	private String consumer_group_id;
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrap_servers;

    @PostConstruct
    public void init() {
        MessengerClientApplication.bootstrap_servers = this.bootstrap_servers;
        MessengerClientApplication.consumer_group_id = this.consumer_group_id;
        System.out.println("Properties set: " + MessengerClientApplication.bootstrap_servers + "   " + MessengerClientApplication.consumer_group_id);
    }
}
