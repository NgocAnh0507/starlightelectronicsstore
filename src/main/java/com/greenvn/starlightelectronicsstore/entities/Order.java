package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity

@Table(name = "PRODUCT_ORDER")
public class Order {
	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderID;
	
	@ManyToOne
	@NotNull(message = "Khách hàng không được để trống!")
	private Customer customer;

	@Column(name = "ORDER_STATUS", columnDefinition = "VARCHAR(55)")
	private OderStatus orderStatus = OderStatus.RECEIVED;
	
	@Column(name = "AMOUNT", columnDefinition = "DECIMAL(11,1)")
	@NotNull(message = "Tổng sản phẩm không được để trống!")
	private Double amount;
	
	@Column(name = "TOTAL", columnDefinition = "DECIMAL(11,1)")
	@NotNull(message = "Tổng tiền không được để trống!")
	private Double total;
	
	@Column(name = "ORDER_DATE", columnDefinition = "DATETIME")
	@NotNull(message = "Ngày đặt hàng không được để trống!")
	private Date orderDate;
	
	@Column(name = "FASTSHIP", columnDefinition = "BOOLEAN NOT NULL")
	@NotNull(message = "Chuyển phát nhanh không được để trống!")
	private Boolean fastShip;

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

	public OderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OderStatus orderStatus) {
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

	public Boolean getFastShip() {
		return fastShip;
	}

	public void setFastShip(Boolean fastShip) {
		this.fastShip = fastShip;
	}

}
