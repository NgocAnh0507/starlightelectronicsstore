package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.ProductReview;
import com.greenvn.starlightelectronicsstore.repository.ProductReviewRepository;

@Service
public class ProductReviewService {

	@Autowired
	private ProductReviewRepository productReviewRepository;
	
	public List<ProductReview> getProductReviews()
	{
		return productReviewRepository.findAll();
	}
	
	public ProductReview addProductReview(ProductReview productReview)
	{
		ProductReview productReviewSaved = productReviewRepository.save(productReview);
		return productReviewSaved;
	}
	
	public ProductReview findProductReviewById(Long productReviewID)
	{
		if(productReviewRepository.findById(productReviewID).isEmpty()) return null;
		return productReviewRepository.findById(productReviewID).get();
	}
	
	public ProductReview updateProductReview(ProductReview productReviewNew, Long productReviewID)
	{
		ProductReview productReview = findProductReviewById(productReviewID);
		if(productReview == null) return null;
		productReview.setRating(productReviewNew.getRating());
		productReview.setCustomer(productReviewNew.getCustomer());
		productReview.setProduct(productReviewNew.getProduct());
		productReview.setReviewDate(productReviewNew.getReviewDate());
		productReview.setReviewDescription(productReviewNew.getReviewDescription());
		return productReviewRepository.save(productReview);
		
	}
	
	public void deleteProductReview(Long productReviewID)
	{
		productReviewRepository.deleteById(productReviewID);
	}
}
