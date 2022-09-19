package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CATEGORY")
public class Category {
	
	@Id
	@Column(name = "CATEGORY_ID")
	@GeneratedValue
	private long categoryID;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}

}
