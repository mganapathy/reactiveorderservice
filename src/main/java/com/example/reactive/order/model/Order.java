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
@Setter
@Getter
@Document
@ToString
@EqualsAndHashCode
@Data
public class Order {
	public Order(String orderId, String orderName, long orderAmount) {
		this.orderId = orderId;
		this.orderName = orderName;
		this.orderAmount=orderAmount;
		// TODO Auto-generated constructor stub
	}
	public Order() {
		
	}
	@Id
	private String orderId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}
	private String orderName;
	private long orderAmount;
}
