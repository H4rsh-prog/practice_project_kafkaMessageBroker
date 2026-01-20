package com.kafka_prac.template;

import org.springframework.data.repository.CrudRepository;

import com.kafka_prac.model.Subscriber;

public interface SubscriberRepo extends CrudRepository<Subscriber, String> {

}
