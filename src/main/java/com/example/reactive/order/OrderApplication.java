package com.example.reactive.order;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.reactive.order.model.Order;
import com.example.reactive.order.repository.OrderRepository;

@SpringBootApplication
public class OrderApplication {

	@Bean
	CommandLineRunner employees(OrderRepository orderRespository) {
		return args -> {
			orderRespository
				.deleteAll()
				.subscribe(null, null, () -> {
					Stream.of(new Order(UUID.randomUUID().toString(),"Grocery_Marimuthu_25/11/2021", 48000),
							new Order(UUID.randomUUID().toString(),"Food_Abimanyu_25/11/2021", 38000),
							new Order(UUID.randomUUID().toString(),"Electronics_Ganapathy_25/11/2021", 28000),
							new Order(UUID.randomUUID().toString(),"Household_Pusbendra_25/11/2021", 58000)
							)
					.forEach(employee -> {
						orderRespository.save(employee)
						.subscribe(System.out::println);
					});
				});
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
