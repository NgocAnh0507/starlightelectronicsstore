package com.greenvn.starlightelectronicsstore.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MANUFACTURER")
public class Manufacturer {

	@Id
	@Column(name = "MANUFACTURER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long manufacturerID;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(55) UNIQUE")
	@NotBlank(message = "Tên không được để trống!")
	private String name;
	
	@OneToOne
	@NotNull(message = "Logo không được để trống!")
	private Image logo;

	@OneToMany(mappedBy = "manufacturer")
	private List<Product> products;

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setManufacturerID(Long manufacturerID) {
		this.manufacturerID = manufacturerID;
	}
	
}
