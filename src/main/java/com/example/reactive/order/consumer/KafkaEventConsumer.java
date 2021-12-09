package com.example.reactive.order.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.reactive.order.model.Order;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaEventConsumer {
	
	public static final String CONST_TOPIC_NAME="test_order";
	
    @KafkaListener(topics = CONST_TOPIC_NAME, groupId = "group_json",
            containerFactory = "oderKafkaListenerFactory")
    public void consumeJson(Order order) {
    	log.info("Consumed Order JSON Message: " + order);
    }
    
    
//	@KafkaListener(topics = CONST_TOPIC_NAME, groupId = "group_id")
//	public void consume(String message) {
//		System.out.println("Consumed message: " + message);
//	}

}