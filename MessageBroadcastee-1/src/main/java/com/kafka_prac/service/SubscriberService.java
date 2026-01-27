package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kafka_prac.model.Subscriber;
import com.kafka_prac.template.SubscriberRepo;

@Service
public class SubscriberService {
	@Autowired
	private SubscriberRepo repo;
	@Value("${spring.kafka.consumer.group-id}")
	private String groupID;
	@Autowired
	MessageHistoryService historyService;
	
	public ResponseEntity<?> addTopic(String topic) {
		Optional<Subscriber> opt_sub = this.repo.findById(groupID);
		if(opt_sub.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Subscriber does not exist, please initialize the subscriber at /_RESERVED_init");
		}
		List<String> subscribedTopics = opt_sub.get().getSubscribedTopics();
		subscribedTopics.add(topic);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.repo.save(new Subscriber(groupID, subscribedTopics)));
	}
	public String init() {
		Optional<Subscriber> opt_sub = this.repo.findById(groupID);
		if(opt_sub.isPresent()) {
			return "The subscriber is already initialized, if you wish to reset the subscriptions please refer to the reset mapping at /_RESERVED_reset";
		}
		this.repo.save(new Subscriber(groupID, new ArrayList<String>()));
		return ("The subscriber was successfully initialized as "+groupID);
	}
	public String reset() {
		this.repo.save(new Subscriber(groupID, new ArrayList<String>()));
		return ("The subscriber was successfully reset");
	}
	public Optional<Subscriber> getSub(){
		return this.repo.findById(groupID);
	}
}
