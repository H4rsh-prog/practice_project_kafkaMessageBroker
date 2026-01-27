package com.kafka_prac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kafka_prac.model.BroadcastedMessage;
import com.kafka_prac.model.TopicHistory;
import com.kafka_prac.template.MessageHistoryRepo;

@Service
public class MessageHistoryService {
	@Autowired
	private MessageHistoryRepo repo;

	public Optional<TopicHistory> addMessage(String topic, BroadcastedMessage message) {
		Optional<TopicHistory> topicHistory = this.repo.findById(topic);
		if(topicHistory.isEmpty()) {
			return Optional.of(this.repo.save(new TopicHistory(topic, new ArrayList<BroadcastedMessage>(List.of(message)))));
		}
		List<BroadcastedMessage> messageHistory = topicHistory.get().getTopicHistory();
		messageHistory.add(message);
		return Optional.of(this.repo.save(new TopicHistory(topic, messageHistory)));
	}

	public List<TopicHistory> fetchAll() {
		return (List<TopicHistory>) this.repo.findAll();
	}

}
