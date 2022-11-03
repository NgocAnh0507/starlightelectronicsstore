package com.greenvn.starlightelectronicsstore.model;

import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;

public class Filter {
    private Long priceMin;
    private Long priceMax;
    private List<Manufacturer> manufacturerList;
    private List<ProductAttribute> attributeList;
    
	
	public Long getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(Long priceMin) {
		this.priceMin = priceMin;
	}
	public Long getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(Long priceMax) {
		this.priceMax = priceMax;
	}
	public List<Manufacturer> getManufacturerList() {
		return manufacturerList;
	}
	public void setManufacturerList(List<Manufacturer> manufacturerList) {
		this.manufacturerList = manufacturerList;
	}
	public List<ProductAttribute> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(List<ProductAttribute> attributeList) {
		this.attributeList = attributeList;
	}
	public Filter(Long priceMin, Long priceMax, List<Manufacturer> manufacturerList,
			List<ProductAttribute> attributeList) {
		super();
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.manufacturerList = manufacturerList;
		this.attributeList = attributeList;
	}
	public Filter() {
		super();
	}
    
    
    
}
