package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PRODUCT_REVIEW")
public class ProductReview {
	@Id
	@Column(name = "PRODUCT_REVIEW_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productReviewID;

	@Column(name = "RATING", columnDefinition = "INT")
	@NotNull(message = "Mức đánh giá không được để trống!")
	private Integer rating;

	@ManyToOne
	@NotNull(message = "Khách hàng không được để trống!")
	private Customer customer;

	@ManyToOne
	@NotNull(message = "Sản phẩm không được để trống!")
	private Product product;

	@Column(name = "REVIEW_DATE", columnDefinition = "DATETIME")
	@NotNull(message = "Ngày đánh giá không được để trống!")
	private Date reviewDate;
	
	@Column(name = "REVIEW_DESCRIPTION", columnDefinition = "VARCHAR(5555)")
	@NotBlank(message = "Mô tả không được để trống!")
	private String reviewDescription;

	public long getProductReviewID() {
		return productReviewID;
	}

	public void setProductReviewID(long productReviewID) {
		this.productReviewID = productReviewID;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		this.reviewDescription = reviewDescription;
	}

}
