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
@Table(name="CUSTOMER")
public class Customer extends Person{

	@Id
	@Column(name = "CUSTOMER_ID", columnDefinition = "VARCHAR(155)")
	@GeneratedValue
	private Long customerID;
	
	@Column(name = "ADDRESS",columnDefinition = "VARCHAR(555)")
	@NotBlank(message = "Địa chỉ không được để trống!")
	private String address;

	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

	@OneToMany(mappedBy = "customer")
	private List<ProductReview> productReviews;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<ProductReview> getProductReviews() {
		return productReviews;
	}

	public void setProductReviews(List<ProductReview> productReviews) {
		this.productReviews = productReviews;
	}
	
	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
