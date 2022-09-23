package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.ProductReview;
import com.greenvn.starlightelectronicsstore.service.ProductReviewService;

@Controller
public class ProductReviewController {

	@Autowired
	private ProductReviewService productReviewService;
	
	@GetMapping("/productReviews")
	public String showProductReviewList(Model model)
	{
		model.addAttribute("productReviews",productReviewService.getProductReviews());
		return "/productReviews";
	}
	
	@GetMapping("/formAddProductReview")
	public String addProductReviewForm(ProductReview productReview) {
		return "add-productReview";
	}
	
	@PostMapping("/addProductReview")
	public String addProductReview(@Valid ProductReview productReview, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-productReview";
		}
		productReviewService.addProductReview(productReview);
		return "";
	}
	
	@GetMapping("/formUpdateProductReview")
	public String updateProductReviewForm(@RequestParam(name = "productReviewID")Long productReviewID, Model model) {
		ProductReview productReview = productReviewService.findProductReviewById(productReviewID);
		model.addAttribute("productReview", productReview);
		return "update-productReview";
	}
	
	@PostMapping("/updateProductReview")
	public String updateProductReview(@RequestParam(name = "productReviewID")Long productReviewID,@Valid ProductReview productReview, BindingResult result, Model model){
		if(result.hasErrors()) {
			productReview.setProductReviewID(productReviewID);
			return "update-productReview";
		}
		productReviewService.updateProductReview(productReview, productReviewID);
		return "";
	}
	
	@GetMapping("/deleteProductReview")
	public String deleteProductReview(@RequestParam(name = "productReviewID")Long productReviewID, Model model) {
		productReviewService.deleteProductReview(productReviewID);
		return "";
	}
}

