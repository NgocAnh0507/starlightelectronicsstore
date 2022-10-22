package com.greenvn.starlightelectronicsstore.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Page<Product> search(String keyword,int pageNo, int pageSize)
	{
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		if (keyword != null) {
            return productRepository.search(pageable,keyword);
        }
        return productRepository.findAll(pageable);
	}
	
	public Product addProduct(Product product)
	{
		Product productSaved = productRepository.save(product);
		return productSaved;
	}

	public Product findProductByName(String Name)
	{
		return productRepository.findProductByName(Name);
	}
	
	public Product findProductById(Long productID)
	{
		return this.productRepository.findById(productID).get();
	}
	
	public Product updateProductAttribute(List<ProductAttribute> productAttributes, String image,Product productNew, Long productID) {
		
		Product product= findProductById(productID);
		product.setDefaultImage(image);
		product.setAttributes(productAttributes);
		product.setPriceSpecialStartDate(productNew.getPriceSpecialStartDate());
		product.setPriceSpecialEndDate(productNew.getPriceSpecialEndDate());
		return productRepository.save(product);
	}
	
	public Product updateProductImage(List<Image> productImages, Long productID) {
		
		Product product= findProductById(productID);
		product.setImages(productImages);
		return productRepository.save(product);
	}
	
	public Product updateProduct(Product productNew, Long productID)
	{
		Product product = findProductById(productID);
		product.setProductSKU(productNew.getProductSKU());
		product.setCategory(productNew.getCategory());
		product.setManufacturer(productNew.getManufacturer());
		product.setProductDescription(productNew.getProductDescription());
		product.setProductName(productNew.getProductName());
		product.setPrice(productNew.getPrice());
		product.setPriceSpecial(productNew.getPriceSpecial());
		product.setQuantity(productNew.getQuantity());
		product.setQuantityOrderMin(productNew.getQuantityOrderMin());
		product.setQuantityOrderMax(productNew.getQuantityOrderMax());
		product.setStatus(productNew.getStatus());
		product.setFreeShip(productNew.getFreeShip());
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long productID)
	{
		productRepository.deleteById(productID);
	}
	
	//Pageable
		public Page<Product> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
				
				//sort
				Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
						Sort.by(sortField).ascending() :
						Sort.by(sortField).descending();
				
				Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
				Page<Product> pageProduct = productRepository.findAll(pageable);
				return pageProduct;
		}
		
		public Page<Product> findProductByCategoryName(String categoryName, int pageNo, int pageSize,String sortField, String sortDirection){
			
			//sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
					Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
			
			Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
			Page<Product> pageProduct = productRepository.findProductByCategoryName(categoryName,pageable);
			return pageProduct;
		}

        public Page<Product> findProductByManufacturerName(String manufacturerName, int pageNo, int pageSize,String sortField, String sortDirection){
            
            //sort
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                    Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
            
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
            Page<Product> pageProduct = productRepository.findProductByManufacturerName(manufacturerName,pageable);
            return pageProduct;
        }
}
