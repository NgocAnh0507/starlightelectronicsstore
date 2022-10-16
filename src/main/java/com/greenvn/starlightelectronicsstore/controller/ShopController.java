package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductReview;
import com.greenvn.starlightelectronicsstore.service.ProductService;

@Controller
public class ShopController {
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String home(Model model,
			
			@RequestParam(name = "page",defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "4") Integer pageSize, @RequestParam(defaultValue ="") String keyword) {
		
		Page<Product> pageProduct=productService.search(keyword, pageNo, pageSize);
		List<Product> products = pageProduct.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("products", products);
		model.addAttribute("keyword", keyword);
		return "shop/home";
	}
	
//	@GetMapping("/shop/byApple")
//	public String listByApple(Model model) {
//		List<Product>products = productService.getProductByManufacturer("APPLE");
//		model.addAttribute("products", products);
//		return "listByApple";
//	}
//	@GetMapping("/shop/bySamsung")
//	public String listBySamsung(Model model) {
//		List<Product>products = productService.getProductByManufacturer("SAMSUNG");
//		model.addAttribute("products", products);
//		return "listBySamsung";
//	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "admin";
	}
	
	private Double countRating(List<ProductReview> list) {
		Double rating = 0.0, size = (double) list.size();
		if(size == 0.0) return rating;
		
		for(ProductReview r : list) {
			rating += (r.getRating()*1.0);
		}
		
		rating /= size;
		return rating;
	}
	
	@GetMapping("/shop/productInfo")
	public String getProduct(@RequestParam(name = "productID")Long productID, HttpServletRequest request, Model model) {

		Product product = productService.findProductById(productID);
		Integer sizeReviews = product.getProductReviews().size();
		Integer sizeAttributes = product.getAttributes().size();
		model.addAttribute("images", product.getImages());
		model.addAttribute("reviews", product.getProductReviews());
		model.addAttribute("reviewsCount", sizeReviews.toString());
		model.addAttribute("attributes", product.getAttributes());
		model.addAttribute("attributesCount", sizeAttributes.toString());
		model.addAttribute("rating", countRating(product.getProductReviews()).toString());
		model.addAttribute("product", product);
		return "shop/product-detail";
	}
	
	@GetMapping("/shop/productDescription")
	public String getProductDescription(@RequestParam(name = "productName")String productName, HttpServletRequest request, Model model) {

		Product product = productService.findProductByName(productName);
		model.addAttribute("product", product);
		return "shop/product-detail-description";
	}
	
	// lọc
	@GetMapping("/shop/productCategoryFilter")
	public String productCategoryFilter(@RequestParam(name = "categoryName")String categoryName,
			@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model) {

		int pageSize = 9;
		Page<Product> pageProduct = productService.findProductByCategoryName(categoryName,pageNo, pageSize,sortField,sortDir);
		List<Product> products = pageProduct.getContent();
		if(products.size() == 0) products = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageProduct.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("products",products);
		return "";
	}
	
	@GetMapping("/shop/thanhtoanvatienich")
	public String thanhToanVaTienIchPage() {
		return "thanhtoanvatienich";
	}
	@GetMapping("/shop/tinmoi")
	public String tinMoiPage() {
		return "tinmoi";
	}
	@GetMapping("/shop/khuyenmai")
	public String khuyenMaiPage() {
		return "khuyenmai";
	}
	@GetMapping("/shop/appvagame")
	public String appvaGamePage() {
		return "appvagame";
	}
	@GetMapping("/shop/tuvan")
	public String tuvanPage() {
		return "tuvan";
	}
	@GetMapping("/shop/quydinhchung")
	public String quydinhPage() {
		return "quydinhchung";
	}
	//
	@GetMapping("/shop/huongdanmuahang")
	public String huongdanmuahangPage() {
		return "huongdanmuahang";
	}
	@GetMapping("/shop/thanhtoanvagiaonhan")
	public String thanhtoanvagiaonhanPage() {
		return "thanhtoanvagiaonhan";
	}
	@GetMapping("/shop/caccauhoi")
	public String caccauhoiPage() {
		return "caccauhoi";
	}
	@GetMapping("/shop/huongdandoitra")
	public String huongdandoitraPage() {
		return "huongdandoitra";
	}
	@GetMapping("/shop/baomatthongtin")
	public String baomatthongtinkhPage() {
		return "baomatthongtinkh";
	}

}
