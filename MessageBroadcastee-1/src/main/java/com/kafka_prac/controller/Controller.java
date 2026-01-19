package com.kafka_prac.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kafka_prac.model.BroadcastedMessage;
import com.kafka_prac.service.KafkaListeners;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Controller {
	@Autowired
	private KafkaListeners listener;
	
	@GetMapping("/")
	public List<BroadcastedMessage> getHistory(){
		return this.listener.getHistory();
	}
	
}
