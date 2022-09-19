package com.greenvn.starlightelectronicsstore.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue
	private long productID;
	
	@Column(name = "PRODUCT_SKU", columnDefinition = "VARCHAR(55)")
	@NotBlank(message = "SKU không được để trống!")
	private String productSKU;
	
	@ManyToOne
	@NotBlank(message = "Danh mục không được để trống!")
	private Category category;
	
	@ManyToOne
	@NotBlank(message = "Hãng sản xuất không được để trống!")
	private Manufacturer manufacturer;

	@OneToOne
	@NotBlank(message = "Ảnh bìa không được để trống!")
	private Image defaultImage;

	@OneToMany(mappedBy = "product")
	private List<Image> images;
	
	@ManyToMany
	private List<ProductAttribute> attributes;
	
	@Column(name = "PRODUCT_DESCRIPTION", columnDefinition = "VARCHAR(5555)")
	@NotBlank(message = "Mô tả không được để trống!")
	private String productDescription;

	@Column(name = "PRICE", columnDefinition = "DECIMAL(11,1)")
	@NotBlank(message = "Giá bán không được để trống!")
	private Double price;

	@Column(name = "PRICE_SPECIAL", columnDefinition = "DECIMAL(11,1)")
	private Double priceSpecial;

	@Column(name = "PRICE_SPECIAL_STARTDATE", columnDefinition = "DATETIME")
	private Date priceSpecialStartDate;
	
	@Column(name = "PRICE_SPECIAL_ENDDATE", columnDefinition = "DATETIME")
	private Date priceSpecialEndDate;

	@Column(name = "QUANTITY", columnDefinition = "INT")
	@NotBlank(message = "Số lượng hiện có không được để trống!")
	private Integer quantity;

	@Column(name = "QUANTITY_ORDER_MIN", columnDefinition = "INT")
	private Integer quantityOrderMin;

	@Column(name = "QUANTITY_ORDER_MAX", columnDefinition = "INT")
	private Integer quantityOrderMax;

	@Column(name = "STATUS", columnDefinition = "BOOLEAN NOT NULL")
	@NotBlank(message = "Trạng thái không được để trống!")
	private Boolean status;

	@Column(name = "FREESHIP", columnDefinition = "BOOLEAN NOT NULL")
	@NotBlank(message = "Free ship không được để trống!")
	private Boolean freeShip;

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public String getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(String productSKU) {
		this.productSKU = productSKU;
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

	public Image getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(Image defaultImage) {
		this.defaultImage = defaultImage;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(Double priceSpecial) {
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
}
