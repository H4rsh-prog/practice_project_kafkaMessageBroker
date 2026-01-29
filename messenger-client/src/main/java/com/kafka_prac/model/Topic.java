package com.kafka_prac.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
	@Id
	private String topic;
	@OneToMany(targetEntity = PayloadMessage.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PayloadMessage> topicHistory;
}
