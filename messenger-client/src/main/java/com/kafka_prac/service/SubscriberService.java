package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kafka_prac.dto.SubscriberRepository;
import com.kafka_prac.model.Subscriber;
import com.kafka_prac.model.Topic;

@Service
public class SubscriberService {
	@Autowired SubscriberRepository repo;
	@Autowired TopicService topicService;
	
	public Optional<Subscriber> getSubscriber(String group_id){
		return this.repo.findById(group_id);
	}
	public ResponseEntity<?> initSubscriber(String group_id) {
		if(getSubscriber(group_id).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(group_id+" IS AN EXISTING SUBSCRIBER");
		}
		this.repo.save(new Subscriber(group_id, new ArrayList<Topic>()));
		return ResponseEntity.ok(group_id+" INITIALIZED AS A SUBSCRIBER");
	}
	public List<String> fetchSubscribedTopics(String group_id){
		return this.repo.fetchSubscribedTopics(group_id);
	}
	public ResponseEntity<?> addTopic(String group_id, String topic){
		Optional<Subscriber> opt_sub = getSubscriber(group_id);
		Optional<Topic> opt_topic = this.topicService.getTopic(topic);
		if(opt_sub.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CLIENT ["+group_id+"] NOT INITIALIZED, PLEASE INITIALIZE BY PUT@/client/id/init/"+group_id);
		}
		if(opt_topic.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topic+" DOES NOT EXISTS");
		}
		if(fetchSubscribedTopics(group_id).contains(topic)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(group_id+" IS ALREADY SUBSCRIBED TO "+topic);
		}
		List<Topic> subscriptionList = opt_sub.get().getSubscriptionList();
		subscriptionList.add(opt_topic.get());
		return ResponseEntity.ok(this.repo.save(new Subscriber(group_id, subscriptionList)));
	}
	public ResponseEntity<?> removeTopic(String group_id, String topic){
		Optional<Subscriber> opt_sub = getSubscriber(group_id);
		Optional<Topic> opt_topic = this.topicService.getTopic(topic);
		if(opt_sub.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(group_id+" IS NOT AN EXISTING SUBSCRIBER");
		}
		if(opt_topic.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topic+" DOES NOT EXISTS");
		}
		if(!fetchSubscribedTopics(group_id).contains(topic)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(group_id+" IS NOT SUBSCRIBED TO "+topic);
		}
		List<Topic> subscriptionList = opt_sub.get().getSubscriptionList();
		subscriptionList.remove(opt_topic.get());
		return ResponseEntity.ok(this.repo.save(new Subscriber(group_id, subscriptionList)));
	}
}
