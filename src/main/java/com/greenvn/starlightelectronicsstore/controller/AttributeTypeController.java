package com.greenvn.starlightelectronicsstore.controller;


import java.util.List;

import javax.servlet.http.HttpServlet;
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

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.service.AttributeTypeService;

@Controller
@RequestMapping(value = "/admin")
public class AttributeTypeController {
	
	@Autowired
	private AttributeTypeService attributeTypeService;

	@GetMapping("/attributeTypes")
	public String showAttributeTypeList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "attributeTypeID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<AttributeType> pageAttributeType = attributeTypeService.findAll(pageNo, pageSize,sortField,sortDir);
		List<AttributeType> attributeTypes = pageAttributeType.getContent();
		if(attributeTypes.size() == 0) attributeTypes = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageAttributeType.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","attributeTypes" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("attributeTypes",attributeTypes);
		return "attributeType-management";
				
	}
	
	@GetMapping("/formAddAttributeType")
	public String addAttributeTypeForm(AttributeType attributeType, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		model.addAttribute("callFrom", callFrom);
		model.addAttribute("callFromID", callFromID);
		return "attributeType-add";
	}
	
	@PostMapping("/addAttributeType")
	public String addAttributeType(@Valid AttributeType attributeType, BindingResult result, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		if (result.hasErrors()) {
			model.addAttribute("notice", "Thêm loại thuộc tính thất bại");
			model.addAttribute("callFrom", callFrom);
			model.addAttribute("callFromID", callFromID);
			return "attributeType-add";
		}
		
		if(attributeTypeService.findAttributeTypeByName(attributeType.getName()) != null) {

			model.addAttribute("notice", "Thêm loại thuộc tính thất bại");
			model.addAttribute("messages","Loại thuộc tính đã tồn tại!");
			model.addAttribute("callFrom", callFrom);
			model.addAttribute("callFromID", callFromID);
			return "attributeType-add";
		}
		else model.addAttribute("messages",null);
		
		attributeTypeService.addAttributeType(attributeType);

		String notice = "addAttributeType";
		if(callFromID != null) return "redirect:" + callFrom + callFromID + "&notice=" + notice;
		return "redirect:" + callFrom + "?notice=" + notice;
	}

	@GetMapping("/formUpdateAttributeType")
	public String updateAttributeTypeForm(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model) {
		AttributeType attributeType =  attributeTypeService.findAttributeTypeById(attributeTypeID);
		model.addAttribute("attributeType", attributeType);
		return "attributeType-update";
	}

	@PostMapping("/updateAttributeType")
	public String updateAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID,@Valid AttributeType attributeType, BindingResult result,HttpServletRequest request, Model model){
		if(result.hasErrors()) {
			model.addAttribute("notice", "Chỉnh sửa loại thuộc tính thất bại");
			attributeType.setAttributeTypeID(attributeTypeID);
			return "attributeType-update";
		}
		AttributeType A = attributeTypeService.findAttributeTypeByName(attributeType.getName());
		if(A != null && A.getAttributeTypeID() != attributeType.getAttributeTypeID()) {
			model.addAttribute("notice", "Chỉnh sửa loại thuộc tính thất bại");
			model.addAttribute("messages","Loại thuộc tính đã tồn tại!");
			return "attributeType-update";
		}
		else model.addAttribute("messages",null);
		
		attributeTypeService.updateAttributeType(attributeType,attributeTypeID);
		
		String notice = "Chỉnh sửa loại thuộc tính thành công!";
		return showAttributeTypeList(1,"attributeTypeID","asc",model,request,notice);
	}

	@GetMapping("/deleteAttributeType")
	public String deleteAttributeType(@RequestParam(name = "attributeTypeID")Long attributeTypeID, Model model,HttpServletRequest request) {
		AttributeType attributeType =  attributeTypeService.findAttributeTypeById(attributeTypeID);
		if(attributeType == null) return "redirect:/admin/attributeTypes";
		if(attributeType.getProductAttributes().size() > 0) {
			model.addAttribute("messages","Không thể xóa loại thuộc tính đang có thuộc tính!");
			return showAttributeTypeList(1,"attributeTypeID","asc",model,request,"Xóa loại thuộc tính thất bại!");
		}
		attributeTypeService.deleteAttributeType(attributeTypeID);
		
		return showAttributeTypeList(1,"attributeTypeID","asc",model,request,"Xóa loại thuộc tính thành công!");
	}
}
