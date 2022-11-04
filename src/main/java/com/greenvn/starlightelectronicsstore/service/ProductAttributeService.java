package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.repository.ProductAttributeRepository;
import com.greenvn.starlightelectronicsstore.repository.ProductRepository;

@Service
public class ProductAttributeService {

	@Autowired
	private ProductAttributeRepository productAttributeRepository;
	
	@Autowired 
	private ProductRepository productRepository;
	
	public List<ProductAttribute> getProductAttributes()
	{
		return productAttributeRepository.findAll();
	}

	public List<ProductAttribute> findProductAttributeByCategoryID(long ID)
	{
		return productAttributeRepository.findProductAttributeByCategoryID(ID);
	}

	public List<ProductAttribute> findProductAttributeByCategoryIDandTypeID(long categoryID, long typeID)
	{
		return productAttributeRepository.findProductAttributeByCategoryIDandTypeID(categoryID,typeID);
	}
	
	public ProductAttribute addProductAttribute(ProductAttribute productAttribute)
	{
		ProductAttribute productAttributeSaved = productAttributeRepository.save(productAttribute);
		return productAttributeSaved;
	}
	
	public ProductAttribute addProductAttribute(ProductAttribute productAttribute,Long productID)
	{
		Product product = productRepository.findById(productID).get();
		Category category = product.getCategory();
		productAttribute.setCategory(category);
		ProductAttribute productAttributeSaved = productAttributeRepository.save(productAttribute);
		return productAttributeSaved;
	}
	
	public ProductAttribute findProductAttributeById(Long productAttributeID)
	{
		if(productAttributeRepository.findById(productAttributeID).isEmpty()) return null;
		return productAttributeRepository.findById(productAttributeID).get();
	}
	
	public ProductAttribute updateProductAttribute(ProductAttribute productAttributeNew, Long productAttributeID)
	{
		ProductAttribute productAttribute = findProductAttributeById(productAttributeID);
		if(productAttribute == null) return null;
		productAttribute.setCategory(productAttributeNew.getCategory());
		productAttribute.setType(productAttributeNew.getType());
		productAttribute.setValue(productAttributeNew.getValue());
		return productAttributeRepository.save(productAttribute);
	}
	
	public void deleteProductAttribute(Long productAttributeID)
	{
		productAttributeRepository.deleteById(productAttributeID);
	}
	
    //Pageable
	public Page<ProductAttribute> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
			
			//sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
					Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
			
			Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
			Page<ProductAttribute> pageProductAttribute = productAttributeRepository.findAll(pageable);
			return pageProductAttribute;
	}
}
