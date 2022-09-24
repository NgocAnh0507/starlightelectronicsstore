package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.greenvn.starlightelectronicsstore.entities.helper.OrderDetailID;

@Entity
@IdClass(OrderDetailID.class)
public class OrderDetail {

	@Id
	@Column(name = "ORDER_ID")
	private Long orderID;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private Long productID;

	@Column(name = "QUANTITY", columnDefinition = "INT")
	@NotBlank(message = "Số lượng hiện có không được để trống!")
	private Integer quantity;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotBlank(message = "Giá bán không được để trống!")
	private Double price;

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
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
