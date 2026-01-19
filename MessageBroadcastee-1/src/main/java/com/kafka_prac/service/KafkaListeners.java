package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.kafka_prac.model.BroadcastedMessage;

import lombok.Getter;
import lombok.Setter;


@Service
public class KafkaListeners {
	@Getter
	private List<String> subscribedTopics = new ArrayList<>(List.of("firstTopic","secretTopic"));
	@Getter
	private List<BroadcastedMessage> history = new ArrayList<>();
	
	@KafkaListener(topicPattern = ".*", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(Message<String> message) {
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
		System.out.println(msg);
		history.add(msg);
	}
}
