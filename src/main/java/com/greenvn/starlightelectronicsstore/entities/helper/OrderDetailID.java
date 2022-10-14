package com.greenvn.starlightelectronicsstore.entities.helper;

import java.io.Serializable;

public class OrderDetailID implements Serializable {
	
	 private long productID;

	 private long orderID;

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public OrderDetailID(long productID, long orderID) {
		super();
		this.productID = productID;
		this.orderID = orderID;
	}

	public OrderDetailID() {
		super();
	}
	 
	 
}
