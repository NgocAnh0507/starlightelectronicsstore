package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PRODUCT_ATTRIBUTE")
public class ProductAttribute {

	@Id
	@Column(name = "PRODUCT_ATTRIBUTE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productAttributeID;
	
	@ManyToOne
	@NotBlank(message = "Danh mục không được để trống!")
	private Category category;

	@ManyToOne
	@NotBlank(message = "Loại thuộc tính không được để trống!")
	private AttributeType type;

	@Column(name = "VALUE", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Giá trị không được để trống!")
	private String value;

	@ManyToMany(mappedBy = "attributes")
	private List<Product> products;

	public long getProductAttributeID() {
		return productAttributeID;
	}

	public void setProductAttributeID(long productAttributeID) {
		this.productAttributeID = productAttributeID;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
