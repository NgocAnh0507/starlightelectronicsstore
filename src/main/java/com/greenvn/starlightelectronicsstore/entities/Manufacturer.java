package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "MANUFACTURER")
public class Manufacturer {

	@Id
	@Column(name = "MANUFACTURER_ID")
	@GeneratedValue
	private long manufacturerID;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;
	
	@OneToOne
	@NotBlank(message = "Logo không được để trống!")
	private Image logo;

	public long getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(long manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}
	
}
