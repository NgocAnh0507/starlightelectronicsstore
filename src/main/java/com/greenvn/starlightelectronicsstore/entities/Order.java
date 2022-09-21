package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PRODUCT_ORDER")
public class Order {
	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue
	private Long orderID;
	
	@ManyToOne
	@NotBlank(message = "Khách hàng không được để trống!")
	private Customer customer;

	@Column(name = "ORDER_STATUS", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Trạng thái không được để trống!")
	private String orderStatus;
	
	@Column(name = "AMOUNT", columnDefinition = "DECIMAL(11,1)")
	@NotBlank(message = "Tổng sản phẩm không được để trống!")
	private Double amount;
	
	@Column(name = "TOTAL", columnDefinition = "DECIMAL(11,1)")
	@NotBlank(message = "Tổng tiền không được để trống!")
	private Double total;
	
	@Column(name = "ORDER_DATE", columnDefinition = "DATETIME")
	@NotBlank(message = "Ngày mua không được để trống!")
	private Date orderDate;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
