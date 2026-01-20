package com.kafka_prac.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@OneToMany(targetEntity = BroadcastedMessage.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BroadcastedMessage> topicHistory;
}
