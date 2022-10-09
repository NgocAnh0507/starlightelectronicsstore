package com.greenvn.starlightelectronicsstore.model;

import java.util.ArrayList;
import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Product;

public class CartInfo {
	private CustomerInfo customerInfo;
	private Long orderNum;
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

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	private CartLineInfo findLineById(Long productID) {
		for (CartLineInfo line : this.cartLines) {
			if (line.getProductInfo().getProductID()==(productID)) {
				return line;
			}
		}
		return null;
	}

	public void addProduct(ProductInfo productInfo, int quantity) {
		CartLineInfo line = this.findLineById(productInfo.getProductID());
		if (line == null) {
			line = new CartLineInfo();
			line.setQuantity(0);
			line.setProductInfo(productInfo);
			this.cartLines.add(line);
		}
		int newQuantity = line.getQuantity() + quantity;
		if (newQuantity <= 0) {
			this.cartLines.remove(line);
		} else {
			line.setQuantity(newQuantity);
		}
	}

	public void updateProduct(Long productID, int quantity) {
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
