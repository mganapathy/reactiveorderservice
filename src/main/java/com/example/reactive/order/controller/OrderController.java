package com.example.reactive.order.controller;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactive.order.model.Order;
import com.example.reactive.order.model.OrderEvent;
import com.example.reactive.order.producer.KafkaEventProducer;
import com.example.reactive.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/order")

@Slf4j
public class OrderController {
	@Autowired
	KafkaEventProducer kafkaEventProducer;
	// spring web flux Mono is equivalent to CompletableFuture non blocking callback
	// operation
	// Mono 0..1 elements
	// Flux 0 .. N elements

	private OrderRepository orderRepository;

	public OrderController(OrderRepository orderRepository) {

		this.orderRepository = orderRepository;
	}

	// Flux 0 .. N elements ... list of values
	@GetMapping("/all")
	public Flux<Order> getAll() {
		log.info("order service getAll api invoked");
		return orderRepository.findAll();
	}

	// Mono 0..1 elements ... single value
	@GetMapping("/{id}")
	public Mono<Order> getOrder(@PathVariable("id") final String orderId) {
		log.info("order service getOrder( api invoked");
		return orderRepository.findById(orderId);
	}

	// Flux 0 .. N elements .... live list of values
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OrderEvent> getOrderEvents() {
		log.info("getOrderEvents API Called");
		return orderRepository.findAll().flatMap(order -> {
			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
			Flux<OrderEvent> employeeEventFlux = Flux
					.fromStream(Stream.generate(() -> new OrderEvent(order, new Date())));
			return Flux.zip(interval, employeeEventFlux).map(Tuple2::getT2)
					.filter(p -> p.getOrder().getOrderName().contains("Marimuthu"));
		});

	}
	@PostMapping("/eventMessage")
	public void publishEventMessage(@RequestBody Order order) {
		log.info("publishEventMessage API invoked...");
		kafkaEventProducer.publishEvent(order);
	}

//	@PostMapping("/eventMessage/{msg}")
//	public void publishString(@PathVariable("msg") String msg) {
//		kafkaEventProducer.publishMessage(msg);
//		log.info("Kafka message published successfully...");
//	}

}
