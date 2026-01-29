package com.kafka_prac.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka_prac.MessengerClientApplication;
import com.kafka_prac.service.SubscriberService;
import com.kafka_prac.service.TopicService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class KafkaController {

    @Autowired private SubscriberService subscriberService;
    @Autowired private TopicService topicService;
	@Autowired private KafkaTemplate<String, String> kafkaTemplate;
	
	@PostMapping("/{topic}")
	public ResponseEntity<?> sendMessage(@RequestParam("payload") String payload, @PathVariable("topic") String topic, HttpServletRequest request){
		System.out.println("\nRECEIVED A REQUEST TO SEND MESSAGE TO TOPIC: "+topic+"\n");
		this.kafkaTemplate.send(topic, 0, UUID.randomUUID().toString(), payload);
		return ResponseEntity.ok("Successfully sent message payload");
	}
	@PutMapping("/id/init")
	public ResponseEntity<?> initGroupId(){
		System.out.println("GROUP ID INITIALIZED "+MessengerClientApplication.consumer_group_id);
		return this.subscriberService.initSubscriber(MessengerClientApplication.consumer_group_id);
	}
	@PutMapping("/topic/init/{topic}")
	public ResponseEntity<?> initTopic(@PathVariable("topic") String topic){
		return this.topicService.initTopic(topic);
	}
	@PutMapping("/topic/{topic}")
	public ResponseEntity<?> addTopic(@PathVariable("topic") String topic){
		return this.subscriberService.addTopic(MessengerClientApplication.consumer_group_id, topic);
	}
	@DeleteMapping("/topic/{topic}")
	public ResponseEntity<?> removeTopic(@PathVariable("topic") String topic){
		return this.subscriberService.removeTopic(MessengerClientApplication.consumer_group_id, topic);
	}
	@DeleteMapping("/message/{messageKey}")
	public ResponseEntity<?> deleteMessage(@PathVariable("messageKey") String messageKey){
		return this.topicService.deleteMessage(messageKey);
	}
}