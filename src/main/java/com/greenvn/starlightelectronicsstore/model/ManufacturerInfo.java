package com.greenvn.starlightelectronicsstore.model;


public class ManufacturerInfo {
	private String name;
	private String categoryName;
    private String logo;
    

    public ManufacturerInfo(String name, String categoryName, String logo) {
        super();
        this.name = name;
        this.categoryName = categoryName;
        this.logo = logo;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
    
}
