package com.kafka_prac.template;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.BroadcastedMessage;

@Repository
public interface BroadcastedMessageRepo extends CrudRepository<BroadcastedMessage, String> {

}
