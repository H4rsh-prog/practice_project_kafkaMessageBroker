package com.kafka_prac.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kafka_prac.model.BroadcastedMessage;
import com.kafka_prac.service.JWTService;
import com.kafka_prac.service.KafkaListeners;
import com.kafka_prac.service.SubscriberService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class Controller {
	@Autowired
	private KafkaListeners listener;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private SubscriberService subService;
	
	@GetMapping("/")
	public List<BroadcastedMessage> getHistory(){
		return this.listener.getHistory();
	}
	@PutMapping("/")
	public ResponseEntity<String> generateToken(){
		return ResponseEntity.ok(this.jwtService.getToken());
	}
	@PutMapping("/{topic}")
	public ResponseEntity<?> subscribeTopic(@PathVariable("topic") String topic, HttpServletRequest request){
		String authHeader = request.getHeader("Authorization");
		if(authHeader!= null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			if(this.jwtService.validate(token)) {
				return this.subService.addTopic(topic);
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Json Web Token");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization Header");
	}
	@PutMapping("/_RESERVED_init")
	public String initializeSubscriber(){
		return this.subService.init();
	}
	@PutMapping("/_RESERVED_reset")
	public String resetSubscriber(){
		return this.subService.reset();
	}
}
