package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import com.greenvn.starlightelectronicsstore.entities.helper.OrderDetailID;

@Entity
@IdClass(OrderDetailID.class)
public class OrderDetail {

	@Id
	@Column(name = "ORDER_ID")
	private long orderID;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private long productID;

	@Column(name = "QUANTITY", columnDefinition = "INT")
	@NotNull(message = "Số lượng hiện có không được để trống!")
	private Integer quantity;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotNull(message = "Giá bán không được để trống!")
	private Double price;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
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
