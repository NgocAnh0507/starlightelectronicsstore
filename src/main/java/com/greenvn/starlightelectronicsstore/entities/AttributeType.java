package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ATTRIBUTE_TYPE")
public class AttributeType {

	@Id
	@Column(name = "ATTRIBUTE_TYPE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attributeTypeID;

	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;
	
	@OneToMany(mappedBy = "type")
	private List<ProductAttribute> productAttributes;

	public void setAttributeTypeID(long attributeTypeID) {
		this.attributeTypeID = attributeTypeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public void setAttributeTypeID(Long attributeTypeID) {
		this.attributeTypeID = attributeTypeID;
	}
	
}
