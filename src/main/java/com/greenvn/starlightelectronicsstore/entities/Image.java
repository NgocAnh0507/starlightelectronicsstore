package com.greenvn.starlightelectronicsstore.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "IMAGE")
public class Image {

	@Id
	@Column(name = "IMAGE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageID;

	@Column(name = "NAME", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "Tên không được để trống!")
	private String name;

	@Column(name = "URL", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "URL không được để trống!")
	private String imageURL;
	
	@ManyToOne
	private Product product;

	public long getImageID() {
		return imageID;
	}

	public void setImageID(long imageID) {
		this.imageID = imageID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
