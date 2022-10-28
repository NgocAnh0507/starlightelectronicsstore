package com.greenvn.starlightelectronicsstore.model;

import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.Product;

public class ProductInfo {
	private long productID;
	private String productName;
	private String productDescription;
	private Double price;
	private String defaultImage;
	private String productSKU;
	
	public ProductInfo() {
		super();
	}
	public ProductInfo(Product product) {
		this.productID = product.getProductID();
		this.productName = product.getProductName();
		this.productDescription = product.getProductDescription();
		if(product.getPriceSpecial() != null) this.price = product.getPriceSpecial();
		else this.price = product.getPrice();
		this.defaultImage = product.getDefaultImage();
		this.productSKU=product.getProductSKU();
	}
	
	public String getProductSKU() {
		return productSKU;
	}
	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
	}
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	
}
