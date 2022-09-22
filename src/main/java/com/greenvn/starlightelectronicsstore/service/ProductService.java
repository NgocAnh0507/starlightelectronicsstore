package com.greenvn.starlightelectronicsstore.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getProducts()
	{
		return productRepository.findAll();
	}
	
	public Product addProduct(Product product)
	{
		Product productSaved = productRepository.save(product);
		return productSaved;
	}
	
	public Product findProductById(Long id)
	{
		return productRepository.findById(id).get();
	}
	
	public Product updateProduct(Product productNew, Long id)
	{
		Product product = findProductById(id);
		product.setProductSKU(productNew.getProductSKU());
		product.setCategory(productNew.getCategory());
		product.setManufacturer(productNew.getManufacturer());
		product.setDefaultImage(productNew.getDefaultImage());
		product.setProductDescription(productNew.getProductDescription());
		product.setPrice(productNew.getPrice());
		product.setPriceSpecial(productNew.getPrice());
		product.setPriceSpecialStartDate(productNew.getPriceSpecialEndDate());
		product.setPriceSpecialEndDate(productNew.getPriceSpecialEndDate());
		product.setQuantity(productNew.getQuantity());
		product.setQuantityOrderMin(productNew.getQuantityOrderMin());
		product.setQuantityOrderMax(productNew.getQuantityOrderMax());
		product.setStatus(productNew.getStatus());
		product.setFreeShip(productNew.getFreeShip());
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long id)
	{
		productRepository.deleteById(id);
	}
	
}
