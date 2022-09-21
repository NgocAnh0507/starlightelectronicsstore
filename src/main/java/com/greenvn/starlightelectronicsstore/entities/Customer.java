package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="CUSTOMER")
public class Customer extends Person{

	@Id
	@Column(name = "CUSTOMER_ID", columnDefinition = "VARCHAR(155)")
	private Long idCustomer;
	
	@Column(name = "ADDRESS",columnDefinition = "VARCHAR(555)")
	@NotBlank(message = "Địa chỉ không được để trống!")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}
	
}
