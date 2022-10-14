package com.greenvn.starlightelectronicsstore.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CustomerInfo {
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date birthday;
	private String street;
	private String district;
	private String city;
	private String email;
	private String numberPhone;

	public CustomerInfo() {
	}

	public CustomerInfo(CustomerInfo customerInfo) {
		this.name = customerInfo.getName();
		this.birthday = customerInfo.getBirthday();
		this.street = customerInfo.getStreet();
		this.district = customerInfo.getDistrict();
		this.city = customerInfo.getCity();
		this.email = customerInfo.getEmail();
		this.numberPhone = customerInfo.getNumberPhone();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
}
