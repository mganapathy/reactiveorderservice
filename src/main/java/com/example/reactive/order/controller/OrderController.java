package com.example.reactive.order.controller;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactive.order.model.Order;
import com.example.reactive.order.model.OrderEvent;
import com.example.reactive.order.repository.OrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/order")
public class OrderController {
//	@Autowired
//	KafkaTemplate<String, Order> kafkaTemplate;
////	
	@Autowired
	KafkaTemplate<String, String> kafkaStrTemplate;
	
	// spring web flux Mono is equivalent to CompletableFuture non blocking callback operation
	// Mono 0..1 elements
	// Flux 0 .. N elements
	
	private OrderRepository orderRepository;
	private static final String TOPIC_NAME = "test_order";
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	// Flux 0 .. N elements ... list of values
	@GetMapping("/all")
	public Flux<Order> getAll(){
		System.out.println("getAll API called");
		return orderRepository.findAll();
	}
	// Mono 0..1 elements ... single value
	@GetMapping("/{id}")
	public Mono<Order> getOrder(@PathVariable("id") final String orderId){
		System.out.println("getOrder API called");
		return orderRepository.findById(orderId);
	}
	// Flux 0 .. N elements .... live list of values
	@GetMapping(value="/events", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OrderEvent> getOrderEvents(){
		System.out.println("getOrderEvents API Called");
		Mono<Integer> a = Mono.just(1);
		Integer b = a.block();
		System.out.println("b === " + a);
		return orderRepository.findAll()
		.flatMap(order -> {
			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
			Flux<OrderEvent> employeeEventFlux = 
					Flux.fromStream(Stream.generate(
							() -> new OrderEvent(order, new Date())));
			return Flux.zip(interval, employeeEventFlux)
			.map(Tuple2::getT2)
			.filter(p -> p.getOrder().getOrderName().contains("Marimuthu"));
		});
		
	}
//	@PostMapping("/publish")
//	public void postOrderEvent(@RequestBody Order order) {
//		System.out.println("postOrderEvent API called");
//		try {
//			kafkaTemplate.send(TOPIC_NAME , order);
//			System.out.println("Event published sucessfully...");
//		} catch(Exception e) {
//			System.out.println("failed to publish kafka msg, Exception msg is " + e.getMessage());
//		}
//		
//	}
	@PostMapping("/publishString/{msg}")
	public void publishString(@PathVariable("msg") String msg) {
		System.out.println("publishString API called");
		try {
			System.out.println("Msg recieved " + msg);
			kafkaStrTemplate.send(TOPIC_NAME , msg);
			System.out.println("Event published sucessfully...");
		} catch(Exception e) {
			System.out.println("failed to publish kafka msg, Exception msg is " + e.getMessage());
		}
		
	}
	}
