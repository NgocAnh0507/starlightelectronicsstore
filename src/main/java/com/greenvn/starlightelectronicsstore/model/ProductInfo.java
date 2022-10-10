package com.greenvn.starlightelectronicsstore.model;

import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.Product;

public class ProductInfo {
	private long productID;
	private String productName;
	private String productDescription;
	private Double priceSpecial;
	private String defaultImage;
	
	
	
	
	public ProductInfo() {
		super();
	}
	public ProductInfo(Product product) {
		this.productID = product.getProductID();
		this.productName = product.getProductName();
		this.productDescription = product.getProductDescription();
		this.priceSpecial = product.getPriceSpecial();
		this.defaultImage = product.getDefaultImage();
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
	public Double getPriceSpecial() {
		return priceSpecial;
	}
	public void setPriceSpecial(Double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	
	
	
}
