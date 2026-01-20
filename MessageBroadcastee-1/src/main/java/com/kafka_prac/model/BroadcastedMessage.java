package com.kafka_prac.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BroadcastedMessage {
	@Id
	private String messageKey;
	private String messageTopic;
	private String messagePayload;
	private Date receivedAt;
}