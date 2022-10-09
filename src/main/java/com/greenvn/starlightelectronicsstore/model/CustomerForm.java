package com.greenvn.starlightelectronicsstore.model;

public class CustomerForm {
	private String firstName;
	private String lastName;
	private String street;
	private String district;
	private String city;
	private String email;
	private String numberPhone;
	private boolean valid;

	public CustomerForm() {
	}

	public CustomerForm(CustomerInfo customerInfo) {
		if (customerInfo != null) {
			this.firstName = customerInfo.getFirstName();
			this.lastName = customerInfo.getLastName();
			this.street = customerInfo.getStreet();
			this.district = customerInfo.getDistrict();
			this.city = customerInfo.getCity();
			this.email = customerInfo.getEmail();
			this.numberPhone = customerInfo.getNumberPhone();
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
