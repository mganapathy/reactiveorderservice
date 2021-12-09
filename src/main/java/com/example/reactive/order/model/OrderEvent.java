package com.example.reactive.order.model;

import java.util.Date;

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
public class OrderEvent {
	private Order order;
	private Date dateProcessed;
}
