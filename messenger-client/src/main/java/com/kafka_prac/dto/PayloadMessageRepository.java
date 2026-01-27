package com.kafka_prac.dto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafka_prac.model.PayloadMessage;

@Repository
public interface PayloadMessageRepository extends CrudRepository<PayloadMessage, String>{
	
}
