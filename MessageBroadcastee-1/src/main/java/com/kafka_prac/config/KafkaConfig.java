package com.kafka_prac.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class KafkaConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String localBootstrapServer;
	@Value("${spring.kafka.consumer.group-id}")
	private String localGroupId;
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String localAutoOffsetReset;
	
	@Bean
	public ConsumerFactory<String, String> getFactory(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.localBootstrapServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, this.localGroupId);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.localAutoOffsetReset);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> getConcurrentContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(getFactory());
		return factory;
	}
}
