package com.kafka_prac.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TopicHistory {
	@Id
	private String topic;
	@ManyToOne(targetEntity = BroadcastedMessage.class, cascade = CascadeType.ALL)
	private List<BroadcastedMessage> topicHistory;
}
