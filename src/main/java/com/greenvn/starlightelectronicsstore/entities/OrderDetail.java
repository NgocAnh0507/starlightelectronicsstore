
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
	private int quantity;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotNull(message = "Giá bán không được để trống!")
	private Long price;
	
	public OrderDetail() {
		super();
	}

	public OrderDetail(OrderDetailID orderDetailID) {
		super();
		this.orderID = orderDetailID.getOrderID();
		this.productID = orderDetailID.getProductID();
	}

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

	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	

}
