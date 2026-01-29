package com.kafka_prac.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, String> {
	@Query(nativeQuery = true, value = "SELECT topic_topic from topic_topic_history where topic_history_message_key=:messageKey")
	Optional<String> fetchTopicNameByMessageKey(@Param("messageKey") String messageKey);
}
