package com.app.pojos;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders {
	private Integer orderId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	private OrderDetails details;
	private Customer custId;
	private OrderType orderType;
	private Bills bill;
	public Orders() {
		// TODO Auto-generated constructor stub
	}
	public Orders(Date orderDate) {
		super();
		this.orderDate = orderDate;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "order_date")
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@OneToOne(mappedBy = "orderId",cascade = CascadeType.ALL,orphanRemoval = true)
	public OrderDetails getDetails() {
		return details;
	}
	public void setDetails(OrderDetails details) {
		this.details = details;
	}
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Customer getCustId() {
		return custId;
	}
	public void setCustId(Customer custId) {
		this.custId = custId;
	}
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type")
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	@JsonIgnore
	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
	public Bills getBill() {
		return bill;
	}
	public void setBill(Bills bill) {
		this.bill = bill;
	}
	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderDate=" + orderDate + "]";
	}
	
}
