package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.repository.ProductAttributeRepository;

@Service
public class ProductAttributeService {

	@Autowired
	private ProductAttributeRepository productAttributeRepository;
	
	public List<ProductAttribute> getProductAttributes()
	{
		return productAttributeRepository.findAll();
	}
	
	public ProductAttribute addProductAttribute(ProductAttribute productAttribute)
	{
		ProductAttribute productAttributeSaved = productAttributeRepository.save(productAttribute);
		return productAttributeSaved;
	}
	
	public ProductAttribute findProductAttributeById(Long id)
	{
		return productAttributeRepository.findById(id).get();
	}
	
	public ProductAttribute updateProductAttribute(ProductAttribute productAttributeNew, Long id)
	{
		ProductAttribute productAttribute = findProductAttributeById(id);
		productAttribute.setCategory(productAttributeNew.getCategory());
		productAttribute.setType(productAttributeNew.getType());
		productAttribute.setValue(productAttributeNew.getValue());
		return productAttributeRepository.save(productAttribute);
	}
	
	public void deleteProductAttribute(Long id)
	{
		productAttributeRepository.deleteById(id);
	}
}
