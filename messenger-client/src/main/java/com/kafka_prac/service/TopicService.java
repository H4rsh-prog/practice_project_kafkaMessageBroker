package com.kafka_prac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka_prac.dto.TopicRepository;
import com.kafka_prac.model.Topic;

@Service
public class TopicService {
	@Autowired TopicRepository repo;
	
	public Optional<Topic> getTopic(String topic){
		return this.repo.findById(topic);
	}
}
