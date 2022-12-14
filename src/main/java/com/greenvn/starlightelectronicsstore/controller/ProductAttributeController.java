package com.greenvn.starlightelectronicsstore.controller;

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

import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.AttributeTypeService;
import com.greenvn.starlightelectronicsstore.service.CategoryService;
import com.greenvn.starlightelectronicsstore.service.ProductAttributeService;

@Controller
@RequestMapping(value = "/admin")
public class ProductAttributeController {
	
	@Autowired
	private ProductAttributeService productAttributeService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AttributeTypeService attributeTypeService;
	
	@GetMapping("/productAttributes")
	public String showProductAttributeList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "productAttributeID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model, HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<ProductAttribute> pageProductAttribute = this.productAttributeService.findAll(pageNo, pageSize,sortField,sortDir);
		List<ProductAttribute> productAttributes = pageProductAttribute.getContent();
		if(productAttributes.size() == 0) productAttributes = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageProductAttribute.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","productAttributes" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("productAttributes",productAttributes);
		return "productAttribute-management";
		
	}
	
	@GetMapping("/formAddProductAttribute")
	public String addProductAttributeForm(ProductAttribute productAttribute, Model model,
			@RequestParam(name= "notice",required = false)String notice) {
		model.addAttribute("categories",this.categoryService.getCategories());
		model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
		model.addAttribute("notice", notice);
		return "productAttribute-add";
	}
	
	@PostMapping("/addProductAttribute")
	public String addProductAttribute(@Valid ProductAttribute productAttribute, BindingResult result, HttpServletRequest request, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("categories",this.categoryService.getCategories());
			model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
			model.addAttribute("notice", "Th??m thu???c t??nh th???t b???i!");
			return "productAttribute-add";
		}
		
		List<ProductAttribute> attributeList = productAttributeService.findProductAttributeByCategoryIDandTypeID(productAttribute.getCategory().getCategoryID(), productAttribute.getType().getAttributeTypeID());
		Boolean check = true;
		for(ProductAttribute pa : attributeList) {
		    if(pa.getValue().equals(productAttribute.getValue())) {
		        check = false;
		        break;
		    }
		}
		
		if(!check) {
			model.addAttribute("notice", "Th??m thu???c t??nh th???t b???i!");
            model.addAttribute("messages","Thu???c t??nh c??ng gi?? tr??? ???? t???n t???i!");
            model.addAttribute("categories",this.categoryService.getCategories());
            model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
            return "productAttribute-add";
		}
		
		this.productAttributeService.addProductAttribute(productAttribute);
		

		return showProductAttributeList(1,"productAttributeID","asc",model,request,"Th??m thu???c t??nh th??nh c??ng!");
	}
	
	@GetMapping("/formUpdateProductAttribute")
	public String updateProductAttributeForm(@RequestParam(name = "productAttributeID")Long productAttributeID, Model model,
			@RequestParam(name= "notice",required = false)String notice) {
		ProductAttribute productAttribute = this.productAttributeService.findProductAttributeById(productAttributeID);
		model.addAttribute("productAttribute", productAttribute);
		model.addAttribute("categories",this.categoryService.getCategories());
		model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
		model.addAttribute("notice", notice);
		return "productAttribute-update";
	}
	
	@PostMapping("/updateProductAttribute")
	public String updateProductAttribute(@RequestParam(name = "productAttributeID")Long productAttributeID,@Valid ProductAttribute productAttribute, BindingResult result,HttpServletRequest request, Model model){
		if(result.hasErrors()) {
			model.addAttribute("notice", "Ch???nh s???a thu???c t??nh th???t b???i!");
			model.addAttribute("productAttribute", productAttribute);
			model.addAttribute("categories",this.categoryService.getCategories());
			model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
			return "productAttribute-update";
		}

        List<ProductAttribute> attributeList = productAttributeService.findProductAttributeByCategoryIDandTypeID(productAttribute.getCategory().getCategoryID(), productAttribute.getType().getAttributeTypeID());
        Boolean check = true;
        for(ProductAttribute pa : attributeList) {
            if(pa.getValue().equals(productAttribute.getValue())) {
            	if(!pa.getCategory().getName().equals(productAttribute.getCategory().getName())
            		|| !pa.getType().getName().equals(productAttribute.getType().getName())) {

            		check = false;
	                break;
            	}
            }
        }
        
        if(!check) {
			model.addAttribute("notice", "Ch???nh s???a thu???c t??nh th???t b???i!");
            model.addAttribute("messages","Thu???c t??nh c??ng gi?? tr??? ???? t???n t???i!");
            model.addAttribute("productAttribute", productAttribute);
            model.addAttribute("categories",this.categoryService.getCategories());
            model.addAttribute("attributeTypes",this.attributeTypeService.getAttributeTypes());
            return "productAttribute-update";
        }
        
		this.productAttributeService.updateProductAttribute(productAttribute, productAttributeID);
		return showProductAttributeList(1,"productAttributeID","asc",model,request,"Ch???nh s???a thu???c t??nh th??nh c??ng!");
	}
	
	@GetMapping("/deleteProductAttribute")
	public String deleteProductAttribute(@RequestParam(name = "productAttributeID")Long productAttributeID, Model model,HttpServletRequest request) {
		ProductAttribute productAttribute = this.productAttributeService.findProductAttributeById(productAttributeID);
		if(productAttribute == null) return "redirect:/admin/productAttributes";
		if(productAttribute.getProducts().size() > 0) {
			model.addAttribute("messages","Kh??ng th??? x??a thu???c t??nh ??ang c?? s???n ph???m!");
			return showProductAttributeList(1,"productAttributeID","asc",model,request,"X??a thu???c t??nh th???t b???i!");
		}
		this.productAttributeService.deleteProductAttribute(productAttributeID);

		return showProductAttributeList(1,"productAttributeID","asc",model,request,"X??a thu???c t??nh th??nh c??ng!");
	}
}
