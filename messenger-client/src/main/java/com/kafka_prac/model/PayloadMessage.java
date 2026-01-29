package com.kafka_prac.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadMessage {
	@Id
	private String messageKey;
	private String messagePayload;
	private String messageTopic;
	@Column(columnDefinition = "datetime")
	private Date messageIAT;
}
