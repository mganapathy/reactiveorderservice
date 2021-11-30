package com.example.reactive.order.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.reactive.order.model.Order;

public interface OrderRepository extends ReactiveMongoRepository<Order, String>{

}
