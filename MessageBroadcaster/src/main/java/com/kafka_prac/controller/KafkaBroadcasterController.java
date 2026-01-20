package com.kafka_prac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka_prac.service.KafkaService;


@RestController
public class KafkaBroadcasterController {
	@Autowired
	private KafkaService service;
	
	@PostMapping("/{topic}")
	public void brodcastMessage(@PathVariable("topic") String topic, @RequestParam("message") String message) {
		this.service.sendMessage(topic, message);
	}
}
