package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CATEGORY")
public class Category {
	
	@Id
	@Column(name = "CATEGORY_ID")
	@GeneratedValue
	private Long categoryID;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	@OneToMany(mappedBy = "category")
	private List<ProductAttribute> productAttributes;

	public long getCategoryID() {
		return categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public List<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
	}

	
}
