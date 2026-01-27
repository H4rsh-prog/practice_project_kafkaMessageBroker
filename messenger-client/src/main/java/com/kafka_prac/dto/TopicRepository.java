package com.kafka_prac.dto;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, String> {
}
