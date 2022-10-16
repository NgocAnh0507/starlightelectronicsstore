package com.greenvn.starlightelectronicsstore.model;

import java.util.ArrayList;
import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Product;

public class CartInfo {
	private CustomerInfo customerInfo;
	// private String feeShip;
	private long orderID;
	private String status;
	private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}
//
//	public String getFeeShip() {
//		return feeShip;
//	}
//
//	public void setFeeShip(String feeShip) {
//		this.feeShip = feeShip;
//	}

	public long getOrderID() {
		return orderID;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	private CartLineInfo findLineById(Long productID) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getProductInfo().getProductID()==(productID)) {
				return line;
			}
		}
		return null;
	}

	public void addProduct(ProductInfo productInfo, Double quantity) {
		CartLineInfo line = this.findLineById(productInfo.getProductID());
		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(0.0);
			line.setProductInfo(productInfo);
			this.cartLines.add(line);
		}
		Double newQuantity = line.getQuantity() + quantity;
		if (newQuantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void updateProduct(Long productID, Double quantity) {
		CartLineInfo line = this.findLineById(productID);
		if (line != null) {
			if (quantity <= 0) {
				this.cartLines.remove(line);
			} else {
				line.setQuantity(quantity);
			}
		}
	}

	public void removeProduct(ProductInfo productInfo) {
		CartLineInfo line = this.findLineById(productInfo.getProductID());
		if (line != null) {
			this.cartLines.remove(line);
		}
	}

	

	public boolean isEmpty() {
		return this.cartLines.isEmpty();
	}

	public int getQuantityTotal() {
		int quantity = 0;
		for (CartLineInfo line : this.cartLines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}

	public double getAmountTotal() {
		double total = 0;
		for (CartLineInfo line : this.cartLines) {
			total += line.getAmount();
		}
		return total;
	}

	public void updateQuantity(CartInfo cartForm) {
		if (cartForm != null) {
			List<CartLineInfo> lines = cartForm.getCartLines();
			for (CartLineInfo line : lines) {
				this.updateProduct(line.getProductInfo().getProductID(), line.getQuantity());
			}
		}
	}
}
