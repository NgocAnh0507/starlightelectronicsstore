package com.greenvn.starlightelectronicsstore.model;

public class CartLineInfo {
	private ProductInfo productInfo;
	private Double quantity;

	public CartLineInfo() {
		this.quantity = 0.0;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return this.productInfo.getPrice() * this.quantity;
	}

}
