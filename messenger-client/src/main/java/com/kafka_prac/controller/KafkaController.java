package com.kafka_prac.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class KafkaController {
	@Autowired private KafkaTemplate<String, String> kafkaTemplate;
	
	@PostMapping("/{topic}")
	public ResponseEntity<?> sendMessage(@RequestParam("payload") String payload, @PathVariable("topic") String topic, HttpServletRequest request){
		System.out.println("\nRECEIVED A REQUEST TO SEND MESSAGE TO TOPIC: "+topic+"\n");
		this.kafkaTemplate.send(topic, 0, UUID.randomUUID().toString(), payload);
		return ResponseEntity.ok("Successfully sent message payload");
	}
}