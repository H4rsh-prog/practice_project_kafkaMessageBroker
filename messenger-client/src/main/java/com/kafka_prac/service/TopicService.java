package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kafka_prac.dto.PayloadMessageRepository;
import com.kafka_prac.dto.TopicRepository;
import com.kafka_prac.model.PayloadMessage;
import com.kafka_prac.model.Topic;

@Service
public class TopicService {
	@Autowired TopicRepository repo;
	@Autowired PayloadMessageRepository messageRepo;
	
	public Optional<Topic> getTopic(String topic){
		return this.repo.findById(topic);
	}
	public Optional<String> fetchTopicNameByMessageKey(String messageKey){
		return this.repo.fetchTopicNameByMessageKey(messageKey);
	}
	public ResponseEntity<?> initTopic(String topic) {
		if(getTopic(topic).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topic+" IS AN EXISTING TOPIC");
		}
		this.repo.save(new Topic(topic, new ArrayList<PayloadMessage>()));
		return ResponseEntity.ok(topic+" INITIALIZED AS A TOPIC");
	}
	public ResponseEntity<?> addMessage(String topic, PayloadMessage message){
		Optional<Topic> opt_topic = getTopic(topic);
		if(opt_topic.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topic+" IS NOT AN EXISTING TOPIC");
		}
		List<PayloadMessage> topicHistory = opt_topic.get().getTopicHistory();
		topicHistory.add(message);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
					this.repo.save(new Topic(topic, topicHistory))
				);
	}
	public ResponseEntity<?> deleteMessage(String messageKey) {
		Optional<PayloadMessage> opt_payload = this.messageRepo.findById(messageKey);
		if(opt_payload.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID MESSAGE-KEY");
		}
		Optional<String> opt_topicName = fetchTopicNameByMessageKey(messageKey);
		if(opt_topicName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UNABLE TO FIND ANY TOPIC MAPPED WITH THE KEY ["+messageKey+"]");
		}
		Optional<Topic> opt_topic = getTopic(opt_topicName.get());
		List<PayloadMessage> topicHistory = opt_topic.get().getTopicHistory();
		topicHistory.remove(opt_payload.get());
		return ResponseEntity.status(HttpStatus.GONE).body(
					this.repo.save(new Topic(opt_topicName.get(), topicHistory))
				);
	}
}
