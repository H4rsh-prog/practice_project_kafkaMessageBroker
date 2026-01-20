package com.kafka_prac.template;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.TopicHistory;

@Repository
public interface MessageHistoryRepo extends CrudRepository<TopicHistory, String> {

}
