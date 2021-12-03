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
//@NoArgsConstructor
//@AllArgsConstructor
@Setter
@Getter
@Document
@ToString
@EqualsAndHashCode
@Data
public class OrderEvent {
	
	public OrderEvent() {
		
	}
	public OrderEvent(Order order, Date date) {
		this.order=order;
		this.dateProcessed=date;
		// TODO Auto-generated constructor stub
	}
	private Order order;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Date getDateProcessed() {
		return dateProcessed;
	}
	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}
	private Date dateProcessed;
}
