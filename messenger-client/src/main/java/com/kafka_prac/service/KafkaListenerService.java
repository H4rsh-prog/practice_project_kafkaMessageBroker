package com.kafka_prac.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.kafka_prac.MessengerClientApplication;
import com.kafka_prac.model.PayloadMessage;


@Service
public class KafkaListenerService {
	@Autowired SubscriberService subscriberService;
	
	@KafkaListener(topicPattern = ".*", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(Message<String> message) {
		System.out.println("````````````````````````LISTENER``````````````````````````````//");
		if(this.subscriberService.getSubscriber(MessengerClientApplication.consumer_group_id).isEmpty()) {
			System.out.println("CLIENT NOT INITIALIZED, PLEASE INITIALIZE BY GET@/client/init");
			System.out.println("//````````````````````````LISTENER``````````````````````````````");
			return;
		}
		List<String> subscriptionList = this.subscriberService.fetchSubscribedTopics(MessengerClientApplication.consumer_group_id);
		Map<String, Object> headerList = message.getHeaders();
		if(headerList.getOrDefault("kafka_receivedTopic", "DEFAULT_TOPIC_NOT_FOUND").equals("DEFAULT_TOPIC_NOT_FOUND")) {
			System.out.println("BROADCAST FAILURE, FAILED TO FETCH TOPIC");
			System.out.println("//````````````````````````LISTENER``````````````````````````````");
			return;
		}
		if(headerList.getOrDefault("kafka_receivedMessageKey", "DEFAULT_KEY_NOT_FOUND").equals("DEFAULT_KEY_NOT_FOUND")) {
			System.out.println("BROADCAST FAILURE, FAILED TO FETCH KEY");
			System.out.println("//````````````````````````LISTENER``````````````````````````````");
			return;
		}
		if(!subscriptionList.contains(headerList.get("kafka_receivedTopic"))) {
			System.out.println("CLIENT IS NOT SUBSCRIBED TO THIS TOPIC, TO SUBSCRIBE PUT@/client/"+headerList.get("kafka_receivedTopic").toString());
			System.out.println("//````````````````````````LISTENER``````````````````````````````");
			return;
		}
		PayloadMessage payload = new PayloadMessage(headerList.get("kafka_receivedMessageKey").toString(),
													message.getPayload(),
													headerList.get("kafka_receivedTopic").toString(),
													new Date(System.currentTimeMillis()));
		System.out.println(payload);
		System.out.println("//````````````````````````LISTENER``````````````````````````````");
	}
}
