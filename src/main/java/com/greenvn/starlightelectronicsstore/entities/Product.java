package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productID;

	@Column(name = "PRODUCT_NAME", columnDefinition = "VARCHAR(555) UNIQUE")
	@NotBlank(message = "Tên sản phẩm không được để trống!")
	private String productName;
	
	@Column(name = "PRODUCT_SKU", columnDefinition = "VARCHAR(55) UNIQUE")
	@NotBlank(message = "SKU không được để trống!")
	private String productSKU;
	
	@Column(name = "PRODUCT_DESCRIPTION", columnDefinition = "VARCHAR(65000)")
	@NotBlank(message = "Mô tả sản phẩm không được để trống!")
	private String productDescription;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotNull(message = "Giá bán không được để trống!")
	private Long price;

	@Column(name = "PRICE_SPECIAL", columnDefinition = "DECIMAL(11,1)")
	private Long priceSpecial;

	
	@Column(name = "PRICE_SPECIAL_STARTDATE", columnDefinition = "DATETIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date priceSpecialStartDate;
	
	
	@Column(name = "PRICE_SPECIAL_ENDDATE", columnDefinition = "DATETIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date priceSpecialEndDate;
	
	

	@Column(name = "QUANTITY", columnDefinition = "INT")
	@NotNull(message = "Số lượng hiện có không được để trống!")
	private Integer quantity;

	@Column(name = "QUANTITY_ORDER_MIN", columnDefinition = "INT")
	private Integer quantityOrderMin;

	@Column(name = "QUANTITY_ORDER_MAX", columnDefinition = "INT")
	private Integer quantityOrderMax;

	@Column(name = "STATUS", columnDefinition = "BOOLEAN NOT NULL")
	@NotNull(message = "Trạng thái không được để trống!")
	private Boolean status;

	@Column(name = "FREESHIP", columnDefinition = "BOOLEAN NOT NULL")
	@NotNull(message = "Giao hàng miễn phí không được để trống!")
	private Boolean freeShip;
	
	@ManyToOne
	@NotNull(message = "Danh mục không được để trống!")
	private Category category;
	
	@ManyToOne
	@NotNull(message = "Hãng sản xuất không được để trống!")
	private Manufacturer manufacturer;

	@Column(name = "DEFAULT_IMAGE", columnDefinition = "VARCHAR(555)")
	private String defaultImage;

	@OneToMany(mappedBy = "product")
	private List<Image> images;

	@OneToMany(mappedBy = "product")
	private List<ProductReview> productReviews;
	
	@ManyToMany
	private List<ProductAttribute> attributes;

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
	}

	
	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(Long priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

	public Date getPriceSpecialStartDate() {
		return priceSpecialStartDate;
	}

	public void setPriceSpecialStartDate(Date priceSpecialStartDate) {
		this.priceSpecialStartDate = priceSpecialStartDate;
	}

	public Date getPriceSpecialEndDate() {
		return priceSpecialEndDate;
	}


	public void setPriceSpecialEndDate(Date priceSpecialEndDate) {
		this.priceSpecialEndDate = priceSpecialEndDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantityOrderMin() {
		return quantityOrderMin;
	}

	public void setQuantityOrderMin(Integer quantityOrderMin) {
		this.quantityOrderMin = quantityOrderMin;
	}

	public Integer getQuantityOrderMax() {
		return quantityOrderMax;
	}

	public void setQuantityOrderMax(Integer quantityOrderMax) {
		this.quantityOrderMax = quantityOrderMax;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getFreeShip() {
		return freeShip;
	}

	public void setFreeShip(Boolean freeShip) {
		this.freeShip = freeShip;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<ProductReview> getProductReviews() {
		return productReviews;
	}

	public void setProductReviews(List<ProductReview> productReviews) {
		this.productReviews = productReviews;
	}

	public List<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

}
