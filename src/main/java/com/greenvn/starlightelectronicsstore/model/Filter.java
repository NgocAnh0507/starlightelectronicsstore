package com.greenvn.starlightelectronicsstore.model;

import java.util.List;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;

public class Filter {
    private Double priceMin;
    private Double priceMax;
    private Manufacturer manufacturer;
    private List<ProductAttribute> attributes;
    
    public Filter(Double priceMin, Double priceMax, Manufacturer manufacturer, List<ProductAttribute> attributes) {
        super();
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.manufacturer = manufacturer;
        this.attributes = attributes;
    }
    
    public Filter() {
        super();
    }


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
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    public List<ProductAttribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }
    
}
