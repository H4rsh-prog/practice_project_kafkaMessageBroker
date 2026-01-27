package com.kafka_prac.dto;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.Subscriber;

@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, String> {
	@Query(nativeQuery = true, value = "SELECT subscription_list_topic FROM subscriber_subscription_list where subscriber_group_id=:group_id")
	public List<String> fetchSubscribedTopics(@Param("group_id") String group_id);
}
