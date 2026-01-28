package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kafka_prac.dto.TopicRepository;
import com.kafka_prac.model.PayloadMessage;
import com.kafka_prac.model.Topic;

@Service
public class TopicService {
	@Autowired TopicRepository repo;
	
	public Optional<Topic> getTopic(String topic){
		return this.repo.findById(topic);
	}
	public ResponseEntity<?> initTopic(String topic) {
		if(getTopic(topic).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topic+" IS AN EXISTING TOPIC");
		}
		this.repo.save(new Topic(topic, new ArrayList<PayloadMessage>()));
		return ResponseEntity.ok(topic+" INITIALIZED AS A TOPIC");
	}
}
