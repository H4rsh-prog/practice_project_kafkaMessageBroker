package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.kafka_prac.model.BroadcastedMessage;

import lombok.Getter;


@Service
public class KafkaListeners {
	@Autowired
	private SubscriberService subService;
	@Autowired
	private MessageHistoryService historyService;
	@Getter
	private List<String> subscribedTopics = new ArrayList<>();
	
	@KafkaListener(topicPattern = ".*", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(Message<String> message) {
		this.subscribedTopics = this.subService.getSub()
												.get()
												.getSubscribedTopics();
		Map<String, Object> headerList = message.getHeaders();
		if(!this.getSubscribedTopics().contains(headerList.get("kafka_receivedTopic"))) {
			return;
		}
		BroadcastedMessage msg = new BroadcastedMessage(
											""+headerList.getOrDefault("kafka_receivedTopic", "DEFAULT_NOT_FOUND"),
											message.getPayload(),
											""+headerList.getOrDefault("kafka_receivedMessageKey", "DEFAULT_NOT_FOUND"),
											new Date(System.currentTimeMillis())
										);
		this.historyService.addMessage((String) headerList.get("kafka_receivedTopic"), msg);
	}
}
