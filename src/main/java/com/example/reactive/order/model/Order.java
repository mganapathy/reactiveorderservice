package com.example.reactive.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Document
@ToString
@EqualsAndHashCode
@Data
public class Order {
	@Id
	private String orderId;
	private String orderName;
	private long orderAmount;
}
