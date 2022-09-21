package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ATTRIBUTE_TYPE")
public class AttributeType {

	@Id
	@Column(name = "ATTRIBUTE_TYPE_ID")
	@GeneratedValue
	private Long attributeTypeID;

	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;

	public long getAttributeTypeID() {
		return attributeTypeID;
	}

	public void setAttributeTypeID(long attributeTypeID) {
		this.attributeTypeID = attributeTypeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
