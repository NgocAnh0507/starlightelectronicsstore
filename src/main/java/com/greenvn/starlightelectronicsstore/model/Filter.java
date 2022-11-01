package com.greenvn.starlightelectronicsstore.model;

import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;

public class Filter {
    private Double priceMin;
    private Double priceMax;
    private List<Manufacturer> manufacturerList;
    private List<ProductAttribute> attributeList;
    
	public Double getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}
	public Double getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(Double priceMax) {
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
	public Filter(Double priceMin, Double priceMax, List<Manufacturer> manufacturerList,
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
