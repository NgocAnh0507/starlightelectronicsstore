package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.entities.Product;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
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
	
	@GetMapping("/products")
	public String showProductList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Product> pageProduct = productService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Product> products = pageProduct.getContent();
		if(products.size() == 0) products = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageProduct.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("products",products);
		return "product-management";
	}
	
	@GetMapping("/formAddProduct")
	public String addProductForm(Product product,Model model) {
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", imageService.getImages());
		return "product-add";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@Valid Product product, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") List<MultipartFile> file) {
		if (result.hasErrors()) {
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		
		if(productService.findProductByName(product.getProductName()) != null)
		{
			model.addAttribute("messages", "Sản phẩm đã tồn tại!");
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			return "product-add";
		}
		else model.addAttribute("messages", null);
		Product productSaved =productService.addProduct(product);

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		storageService.storeImageMultiFiles(file, uploadRootPath, productSaved);
		
		return "redirect:/admin/formAttributesForProduct?productID="+productSaved.getProductID();
		//return attributesForProduct(productSaved.getProductID(),model);
	}
	
	@GetMapping("/formAttributesForProduct")
	public String attributesForProduct(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(product.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}

		model.addAttribute("images",product.getImages());
		model.addAttribute("productAttributes",productAttributes);
		model.addAttribute("attributeTypes",attributeTypes);
		model.addAttribute("product", product);
		return "product-editAttribute";
	}
	
	@PostMapping("/attributesForProduct")
	public String attributesForProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model) {
		Product productOld = productService.findProductById(productID);
		
		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		List<ProductAttribute> productAttributes =  productAttributeService.findProductAttributeByCategoryID(productOld.getCategory().getCategoryID());
		for(ProductAttribute PA : productAttributes) {
			if(!attributeTypes.contains(PA.getType())) attributeTypes.add(PA.getType());
		}
		
		if(productOld.getPriceSpecial() != null)
		{
			if(product.getPriceSpecialEndDate() == null || product.getPriceSpecialStartDate() == null) {
				
				model.addAttribute("messages", "Thời gian khuyến mãi không được để trống!");
				model.addAttribute("images",productOld.getImages());
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttribute";
			}
			else if(product.getPriceSpecialEndDate().before(product.getPriceSpecialStartDate())) 
			{
				model.addAttribute("messages", "Ngày bắt đầu khuyến mãi không thể muộn hơn ngày kết thúc!");
				model.addAttribute("images",productOld.getImages());
				model.addAttribute("productAttributes",productAttributes);
				model.addAttribute("attributeTypes",attributeTypes);
				model.addAttribute("product", productOld);
				return "product-editAttribute";
			}
		}
		productService.updateProductAttribute(product.getAttributes(),product.getDefaultImage(),product, productID);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/formUpdateProduct")
	public String updateProductForm(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		model.addAttribute("product", product);
		model.addAttribute("categories",categoryService.getCategories());
		model.addAttribute("manufacturers", manufacturerService.getManufacturers());
		model.addAttribute("images", product.getImages());
		return "product-update";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestParam(name = "productID")Long productID,@Valid Product product, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") List<MultipartFile> file){
		if(result.hasErrors()) {
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", product.getImages());
			return "product-update";
		}
		
		Product P = productService.findProductByName(product.getProductName());
		if(P != null && P.getProductID() != product.getProductID()) {
			model.addAttribute("messages", "Sản phẩm đã tồn tại!");
			model.addAttribute("product", product);
			model.addAttribute("categories",categoryService.getCategories());
			model.addAttribute("productAttributes", productAttributeService.getProductAttributes());
			model.addAttribute("manufacturers", manufacturerService.getManufacturers());
			model.addAttribute("images", imageService.getImages());
			return "product-update";
		}
			
		else model.addAttribute("messages", null);
		
		productService.updateProduct(product, productID);

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		storageService.storeImageMultiFiles(file, uploadRootPath, product);
		
		return "redirect:/admin/formAttributesForProduct?productID="+product.getProductID();
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam(name = "productID")Long productID, Model model) {
		Product product = this.productService.findProductById(productID);
		for(Image i : product.getImages()) {
			imageService.deleteImage(i.getImageID());
		}
		productService.deleteProduct(productID);
		return "redirect:/admin/products";
	}
}
