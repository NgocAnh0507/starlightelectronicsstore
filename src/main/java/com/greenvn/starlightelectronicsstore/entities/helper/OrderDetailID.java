package com.greenvn.starlightelectronicsstore.entities.helper;

import java.io.Serializable;

public class OrderDetailID implements Serializable {
	
	 private Long productID;

	 private Long orderID;

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public OrderDetailID(Long productID, Long orderID) {
		super();
		this.productID = productID;
		this.orderID = orderID;
	}
	 
	 
}
