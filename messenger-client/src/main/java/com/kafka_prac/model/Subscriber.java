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
public class Subscriber {
	@Id
	private String groupId;
	@OneToMany(targetEntity = Topic.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Topic> subscriptionList;
}
