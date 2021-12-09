package com.example.reactive.order.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.reactive.order.model.Order;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaEventProducer {
	@Autowired
	KafkaTemplate<String, Order> kafkaTemplate;
//	@Autowired
//	KafkaTemplate<String, String> kafkaStrTemplate;
	
	private static final String TOPIC_NAME = "test_order";
	
	public void publishEvent(Order order) {
		log.info("publishEvent API called, topic name " + TOPIC_NAME);
		try {
			kafkaTemplate.send(TOPIC_NAME , order);
			log.info("Event published sucessfully...");
		} catch(Exception e) {
			e.printStackTrace();
			log.info("failed to publish kafka msg, Exception msg is " + e.getMessage());
		}
		
	}
//	public void publishMessage(String msg) {
//		log.info("publishString API called");
//		try {
//			log.info("Msg recieved " + msg);
//			kafkaStrTemplate.send(TOPIC_NAME , msg);
//			log.info("Event published sucessfully...");
//		} catch(Exception e) {
//			log.info("failed to publish kafka msg, Exception msg is " + e.getMessage());
//		}
//		
//	}
}
