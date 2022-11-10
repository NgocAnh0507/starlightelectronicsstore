package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.OrderDetail;
import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
import com.greenvn.starlightelectronicsstore.service.OderDetailService;
import com.greenvn.starlightelectronicsstore.service.ProductAttributeService;
import com.greenvn.starlightelectronicsstore.service.ProductService;
import com.greenvn.starlightelectronicsstore.service.StorageService;

@Controller
@RequestMapping(value = "/admin")
public class ProductController {

	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private OderDetailService oderDetailService;
	
	@GetMapping("/products")
	public String showProductList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<Product> pageProduct = productService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Product> products = pageProduct.getContent();
		if(products.size() == 0) products = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageProduct.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","products" );
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("products",products);
		return "product-management";
	}
	
	@GetMapping("/formAddProduct")
	public String addProductForm(Product product,Model model,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", imageService.getImages());
		return "product-add";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid Product product, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") List<MultipartFile> file,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		if (result.hasErrors()) {
			model.addAttribute("notice", "Thêm sản phẩm thất bại");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		
		if(productService.findProductByName(product.getProductName()) != null)
		{
			model.addAttribute("notice", "Thêm sản phẩm thất bại");
			model.addAttribute("messages", "Tên sản phẩm đã tồn tại!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		else model.addAttribute("messages", null);
		
		if(productService.findProductBySKU(product.getProductSKU()) != null)
		{
			model.addAttribute("notice", "Thêm sản phẩm thất bại");
			model.addAttribute("messagesSKU", "Mã SKU đã tồn tại!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		else model.addAttribute("messagesSKU", null);
		
		if(product.getQuantityOrderMin() != null && product.getQuantityOrderMax() != null
			&&	product.getQuantityOrderMin() > product.getQuantityOrderMax())
		{
			model.addAttribute("notice", "Thêm sản phẩm thất bại");
			model.addAttribute("messagesOrder", "Số lượng đặt hàng tối thiểu không được lớn hơn số lượng đặt hàng tối đa!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		else model.addAttribute("messagesOrder", null);
		
		Product productSaved =productService.addProduct(product);

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		storageService.storeImageMultiFiles(file, uploadRootPath, productSaved);
		
		return "redirect:/admin/formAttributesForAddProduct?productID="+productSaved.getProductID();
		//return attributesForProduct(productSaved.getProductID(),model);
	}
	
	@GetMapping("/formAttributesForAddProduct")
	public String attributesForAddProduct(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(product.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}
		if(attributeTypes.size() == 0) attributeTypes = null;
		
		List<Image> images = product.getImages();
		if(images.size() == 0) images = null;

		model.addAttribute("images",images);
		model.addAttribute("productAttributes",productAttributes);
		model.addAttribute("attributeTypes",attributeTypes);
		model.addAttribute("product", product);
		return "product-editAttributeForAdd";
	}
	
	@PostMapping("/attributesForAddProduct")
	public String attributesForAddProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, HttpServletRequest request, Model model) {
		Product productOld = productService.findProductById(productID);
		if(productOld == null) return "redirect:/admin/products";
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(productOld.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}
		if(attributeTypes.size() == 0) attributeTypes = null;

		List<Image> images = productOld.getImages();
		if(images.size() == 0) images = null;
		
		if(productOld.getPriceSpecial() != null)
		{
			if(product.getPriceSpecialEndDate() == null || product.getPriceSpecialStartDate() == null) {

				model.addAttribute("notice", "Thêm sản phẩm thất bại");
				model.addAttribute("messages", "Thời gian khuyến mãi không được để trống!");
				model.addAttribute("images",images);
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttributeForAdd";
			}
			else if(product.getPriceSpecialEndDate().before(product.getPriceSpecialStartDate())) 
			{
				model.addAttribute("notice", "Thêm sản phẩm thất bại");
				model.addAttribute("messages", "Ngày bắt đầu khuyến mãi không thể muộn hơn ngày kết thúc!");
				model.addAttribute("images",images);
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttributeForAdd";
			}
		}
		productService.updateProductAttribute(product.getAttributes(),product.getDefaultImage(),product, productID);
		
		
		return showProductList(1,"productID","asc",model,request,"Thêm sản phẩm thành công!");
	}
	
	@GetMapping("/formUpdateProduct")
	public String updateProductForm(@RequestParam(name = "productID")Long productID, Model model,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		Product product = this.productService.findProductById(productID);
		model.addAttribute("product", product);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", product.getImages());
		return "product-update";
	}
	
	
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") List<MultipartFile> file,
			@RequestParam(name= "notice",required = false)String notice) {
			model.addAttribute("notice", notice);
		if(result.hasErrors()) {
			Product P = productService.findProductByName(product.getProductName());
			model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", P.getImages());
			return "product-update";
		}
		
		Product P = productService.findProductByName(product.getProductName());
		if(P != null && P.getProductID() != product.getProductID()) {
			model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
			model.addAttribute("messages", "Tên sản phẩm đã tồn tại!");
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("productAttributes", productAttributeService.getProductAttributes());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", P.getImages());
			return "product-update";
		}
		else model.addAttribute("messages", null);
		
		P = productService.findProductBySKU(product.getProductSKU());
		if(P != null && P.getProductID() != product.getProductID())
		{
			model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
			model.addAttribute("messagesSKU", "Mã SKU đã tồn tại!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-update";
		}
		else model.addAttribute("messagesSKU", null);
		

		if(product.getQuantityOrderMin() != null && product.getQuantityOrderMax() != null
			&&	product.getQuantityOrderMin() > product.getQuantityOrderMax())
		{
			model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
			model.addAttribute("messagesOrder", "Số lượng đặt hàng tối thiểu không được lớn hơn số lượng đặt hàng tối đa!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-update";
		}
		else model.addAttribute("messagesOrder", null);
		
		productService.updateProduct(product, productID);

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		storageService.storeImageMultiFiles(file, uploadRootPath, product);
		return "redirect:/admin/formAttributesForUpdateProduct?productID="+product.getProductID();
	}
	
	@GetMapping("/formAttributesForUpdateProduct")
	public String attributesForUpdateProduct(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(product.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}
		if(attributeTypes.size() == 0) attributeTypes = null;
		
		List<Image> images = product.getImages();
		if(images.size() == 0) images = null;

		model.addAttribute("images",images);
		model.addAttribute("productAttributes",productAttributes);
		model.addAttribute("attributeTypes",attributeTypes);
		model.addAttribute("product", product);
		return "product-editAttributeForUpdate";
	}
	
	@PostMapping("/attributesForUpdateProduct")
	public String attributesForUpdateProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, HttpServletRequest request, Model model) {
		Product productOld = productService.findProductById(productID);
		if(productOld == null) return "redirect:/admin/products";
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(productOld.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}
		if(attributeTypes.size() == 0) attributeTypes = null;

		List<Image> images = productOld.getImages();
		if(images.size() == 0) images = null;
		
		if(productOld.getPriceSpecial() != null)
		{
			if(product.getPriceSpecialEndDate() == null || product.getPriceSpecialStartDate() == null) {

				model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
				model.addAttribute("messages", "Thời gian khuyến mãi không được để trống!");
				model.addAttribute("images",images);
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttributeForUpdate";
			}
			else if(product.getPriceSpecialEndDate().before(product.getPriceSpecialStartDate())) 
			{
				model.addAttribute("notice", "Chỉnh sửa sản phẩm thất bại");
				model.addAttribute("messages", "Ngày bắt đầu khuyến mãi không thể muộn hơn ngày kết thúc!");
				model.addAttribute("images",images);
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttributeForUpdate";
			}
		}
		productService.updateProductAttribute(product.getAttributes(),product.getDefaultImage(),product, productID);
		
		
		return showProductList(1,"productID","asc",model,request,"Chỉnh sửa sản phẩm thành công!");
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam(name = "productID")Long productID, Model model, HttpServletRequest request) {
		Product product = this.productService.findProductById(productID);
		if(product == null) return "redirect:/admin/products";
		List<OrderDetail> orderDetails = oderDetailService.getOrderDetails();
		boolean check = true;
		for(OrderDetail od : orderDetails) {
			if(od.getProductID() == product.getProductID()) {
				check = false;
				break;
			}
		}
		if(!check) {
			model.addAttribute("deleteMessage","Không thể xóa sản phẩm đang có đơn hàng!");
			return showProductList(1,"productID","asc",model,request,"Xóa sản phẩm thất bại!");
		}
		for(Image i : product.getImages()) {
			imageService.deleteImage(i.getImageID());
		}
		productService.deleteProduct(productID);
		return showProductList(1,"productID","asc",model,request,"Xóa sản phẩm thành công!");
	}
	@GetMapping("/fullInfo")
	public String fullInfo(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		model.addAttribute("product", product);
		return "fullInfoProduct";
	}
	
	@GetMapping("/productDescription")
	public String getProductDescription(@RequestParam(name = "productName")String productName, HttpServletRequest request, Model model) {
		
		Product product = productService.findProductByName(productName);
		if(!product.getProductName().equals(productName)) model.addAttribute("product", null);
		else model.addAttribute("product", product);
		return "product-detail-description-admin";
	}
}
