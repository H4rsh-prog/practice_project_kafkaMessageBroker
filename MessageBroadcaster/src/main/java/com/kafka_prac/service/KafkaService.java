package com.kafka_prac.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Service
public class KafkaService {
	@Autowired
	private KafkaTemplate<String, String> template;
	
	public void sendMessage(String topic, String message) {
		CompletableFuture<SendResult<String, String>> sr = this.template.send(topic, 0, UUID.randomUUID().toString(), message);
	}
}
