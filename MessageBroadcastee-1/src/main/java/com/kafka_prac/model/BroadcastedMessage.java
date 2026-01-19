package com.kafka_prac.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastedMessage {
	private String messageTopic;
	private String messagePayload;
	private String messageKey;
	private Date receivedAt;
}