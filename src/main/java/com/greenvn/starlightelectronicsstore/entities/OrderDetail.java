package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


public class OrderDetail {

	
	@Column(name = "ORDER_ID")
	private Order orderID;
	
	@Column(name = "PRODUCT_ID")
	private Product productID;

	@Column(name = "QUANTITY", columnDefinition = "INT")
	@NotBlank(message = "Số lượng hiện có không được để trống!")
	private Integer quantity;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotBlank(message = "Giá bán không được để trống!")
	private Double price;

	public Order getOrderID() {
		return orderID;
	}

	public void setOrderID(Order order) {
		this.orderID = order;
	}

	public Product getProductID() {
		return productID;
	}

	public void setProductID(Product product) {
		this.productID = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
