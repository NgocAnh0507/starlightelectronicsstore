package com.greenvn.starlightelectronicsstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.entities.ProductReview;
import com.greenvn.starlightelectronicsstore.model.CartInfo;
import com.greenvn.starlightelectronicsstore.model.CartLineInfo;
import com.greenvn.starlightelectronicsstore.model.Filter;
import com.greenvn.starlightelectronicsstore.model.ManufacturerInfo;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
import com.greenvn.starlightelectronicsstore.service.ProductAttributeService;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.utils.Utils;

@Controller
public class ShopController {
	@Autowired
	private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductAttributeService productAttributeService;
    
	@GetMapping("/")
	public String home(Model model,
			
			@RequestParam(name = "page",defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "8") Integer pageSize, @RequestParam(defaultValue ="") String keyword,HttpServletRequest request) {
	    
        // Phải có cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		Page<Product> pageProduct=productService.search(keyword, pageNo, pageSize);
		List<Product> productList = pageProduct.getContent();
        List<Product> products = new ArrayList<Product>();

		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date date= new java.util.Date(millis);
		
		//Lọc chỉ lấy những product có status là true (được phép bán)
		for(Product pro : productList) 
		{
		    if(pro.getStatus() == true) {
		    	//Kiểm tra ngày giảm giá
		    	if(pro.getPriceSpecial() != null) {
		    		if(pro.getPriceSpecialEndDate().before(date) || pro.getPriceSpecialStartDate().after(date)) pro.setPriceSpecial(null);
		    	}
		        products.add(pro);
		    }
		}
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("products", products);
		model.addAttribute("keyword", keyword);
		CartInfo cartInfo = Utils.getCartInSession(request);
		request.getSession().setAttribute("itemCount", cartInfo.getQuantityTotal());
		return "shop/home";
	}

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

        // Phải có cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
		Product product = productService.findProductById(productID);
		
		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date date= new java.util.Date(millis);
		
    	//Kiểm tra ngày giảm giá
    	if(product.getPriceSpecial() != null) {
    		if(product.getPriceSpecialEndDate().before(date) || product.getPriceSpecialStartDate().after(date)) {
    			model.addAttribute("checkSpecialPrice", false);
    		}
    		else model.addAttribute("checkSpecialPrice", true);
    	}
    	else model.addAttribute("checkSpecialPrice", false);
    	
    	//Số lượng hiện có phải lớn hơn số lượng đặt hàng tối thiểu thì mới tính còn hàng
    	if(product.getQuantityOrderMin() != null) 
    	{
	    	Integer quantity_minOder = product.getQuantity() - product.getQuantityOrderMin();
	    	if(quantity_minOder < 0) quantity_minOder=0;
	    	model.addAttribute("quantity_minOder", quantity_minOder);
    	}
    	else model.addAttribute("quantity_minOder", product.getQuantity());
		
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
	
	// lọc theo Danh mục
	@GetMapping("/shop/productCategoryFilter")
	public String productCategoryFilter(@RequestParam(name = "categoryName")String categoryName,
            @RequestParam(name = "manufacturerName", required = false,defaultValue = "")String manufacturerName,
			@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productName") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model) {
	    
	    // Phải có cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
        //Chỉ lấy Manufacturer có product thuộc category đang lọc
        List<Manufacturer> manufacturers = manufacturerService.getManufacturersByCategory(categoryName);
        if(manufacturers.size() == 0) manufacturers = null;
        model.addAttribute("manufacturers", manufacturers);
        
        Category category = categoryService.findCategoryByName(categoryName);
        
		int pageSize = 9;
		Page<Product> pageProduct = productService.findProductByCategoryName(categoryName,pageNo, pageSize,sortField,sortDir);
		List<Product> productList = pageProduct.getContent();
		List<Product> products = new ArrayList<Product>();
		
		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date date= new java.util.Date(millis);
		
		//Lọc chỉ lấy những product có status là true (được phép bán)
		for(Product pro : productList) 
		{
		    if(pro.getStatus() == true) {
		    	//Kiểm tra ngày giảm giá
		    	if(pro.getPriceSpecial() != null) {
		    		if(pro.getPriceSpecialEndDate().before(date) || pro.getPriceSpecialStartDate().after(date)) pro.setPriceSpecial(null);
		    	}
		        products.add(pro);
		    }
		}
        
        List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
        List<ProductAttribute> attributes =  productAttributeService.findProductAttributeByCategoryID(category.getCategoryID());
        for(ProductAttribute A : attributes) {
            if(!attributeTypes.contains(A.getType())) attributeTypes.add(A.getType());
        }
        if(attributeTypes.size() == 0) attributeTypes = null;
        
        model.addAttribute("attributes",attributes);
        model.addAttribute("attributeTypes",attributeTypes);
        
		Filter filter = new Filter(null,null,manufacturers,attributes);

        if(manufacturerName.length() != 0) {
        	Manufacturer m = manufacturerService.findManufacturerByName(manufacturerName);
        	List<Manufacturer> manufacturerList = new ArrayList<Manufacturer>();
        	manufacturerList.add(m);
        	filter.setManufacturerList(manufacturerList);
        	products = productService.filter(filter, products);
        }

        if(products.size() == 0) products = null;
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", pageProduct.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("products",products);
        model.addAttribute("filter",filter);
        model.addAttribute("categoryName",categoryName);
		return "shop/shop-products";
	}
	
	
	
	@PostMapping("/shop/productFilter")
    public String productFilter(Filter filter, Model model,
            HttpServletRequest request, @RequestParam(name = "categoryName")String categoryName,
            @RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
            @RequestParam(name= "sortField",required = false,defaultValue = "productName") String sortField,
            @RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir) {

        // Phải có cho layout-shop
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        List<ManufacturerInfo> manufacturerInfos = manufacturerService.getManufacturerInfoHaveProduct();
        model.addAttribute("manufacturerInfos", manufacturerInfos);
        
        //Chỉ lấy Manufacturer có product thuộc category đang lọc
        List<Manufacturer> manufacturers = manufacturerService.getManufacturersByCategory(categoryName);
        model.addAttribute("manufacturers", manufacturers);
        
        Category category = categoryService.findCategoryByName(categoryName);
        
        int pageSize = 9;
        Page<Product> pageProduct = productService.findProductByCategoryName(categoryName,pageNo, pageSize,sortField,sortDir);
        List<Product> productList = pageProduct.getContent();
        List<Product> products = new ArrayList<Product>();

		// Ngày hiện tại
		long millis=System.currentTimeMillis(); 
		Date date= new java.util.Date(millis);
		
		//Lọc chỉ lấy những product có status là true (được phép bán)
		for(Product pro : productList) 
		{
		    if(pro.getStatus() == true) {

		    	//Kiểm tra ngày giảm giá
		    	if(pro.getPriceSpecial() != null) {
		    		if(pro.getPriceSpecialEndDate().before(date) || pro.getPriceSpecialStartDate().after(date)) pro.setPriceSpecial(null);
		    	}
		    	if(pro.getPriceSpecial() != null) {
			        products.add(pro);
			    }
		    }
		}
        
        products = productService.filter(filter, products);
        
        List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
        List<ProductAttribute> attributes =  productAttributeService.findProductAttributeByCategoryID(category.getCategoryID());
        for(ProductAttribute A : attributes) {
            if(!attributeTypes.contains(A.getType())) attributeTypes.add(A.getType());
        }
        if(attributeTypes.size() == 0) attributeTypes = null;
       
        model.addAttribute("attributes",attributes);
        model.addAttribute("attributeTypes",attributeTypes);

        if(products.size() == 0) products = null;
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", pageProduct.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("products",products);
        model.addAttribute("categoryName",categoryName);

        return "shop/shop-products";
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
