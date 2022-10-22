package com.greenvn.starlightelectronicsstore.model;


public class ManufacturerInfo {
	private String categoryName;
    private String logo;
    
    public ManufacturerInfo(String categoryName, String logo) {
        super();
        this.categoryName = categoryName;
        this.logo = logo;
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
