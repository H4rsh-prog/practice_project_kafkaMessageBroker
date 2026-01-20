package com.kafka_prac.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BroadcastedMessage {
	private String messageTopic;
	private String messagePayload;
	@Id
	@JoinColumn(table = "topic_history")
	private String messageKey;
	@Column(columnDefinition = "datetime")
	private Date receivedAt;
}