package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="CUSTOMER")
public class Customer extends Person{

	@Column(name = "ADDRESS",columnDefinition = "VARCHAR(555)")
	@NotBlank(message = "Địa chỉ không được để trống!")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
