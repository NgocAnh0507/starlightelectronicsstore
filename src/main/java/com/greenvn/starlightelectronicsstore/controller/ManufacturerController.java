package com.greenvn.starlightelectronicsstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;

@Controller
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/manufacturers")
	public String showManufacturerList(@RequestParam(name = "page", required = false,defaultValue = "1") int pageNo,
			@RequestParam(name= "sortField",required = false,defaultValue = "manufacturerID") String sortField,
			@RequestParam(name= "sortDir",required = false,defaultValue = "asc")String sortDir,
			Model model)
	{
		int pageSize = 9;
		Page<Manufacturer> pageManufacturer = manufacturerService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Manufacturer> manufacturers = pageManufacturer.getContent();
		if(manufacturers.size() == 0) manufacturers = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageManufacturer.getTotalPages());
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("manufacturers",manufacturers);
		return "manufacturer-management";
				
	}
	
	@GetMapping("/formAddManufacturer")
	public String addManufacturerForm(Manufacturer manufacturer,Model model) {
		model.addAttribute("images", imageService.getImages());
		return "manufacturer-add";
	}
	
	@PostMapping("/addManufacturer")
	public String addManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("images", imageService.getImages());
			return "manufacturer-add";
		}

		if(manufacturerService.findManufacturerByName(manufacturer.getName()) != null) {
			
			model.addAttribute("messages","Hãng sản xuất đã tồn tại!");
			model.addAttribute("images", imageService.getImages());
			return "manufacturer-add";
		}
		else model.addAttribute("messages",null);
		
		manufacturerService.addManufacturer(manufacturer);
		return "redirect:/manufacturers";
	}

	@GetMapping("/formUpdateManufacturer")
	public String updateManufacturerForm(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		Manufacturer manufacturer =  manufacturerService.findManufacturertById(manufacturerID);
		model.addAttribute("manufacturer", manufacturer);
		model.addAttribute("images", imageService.getImages());
		return "manufacturer-update";
	}

	@PostMapping("/updateManufacturer")
	public String updateManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID,@Valid Manufacturer manufacturer, BindingResult result, Model model){
		if(result.hasErrors()) {
			model.addAttribute("manufacturer", manufacturer);
			model.addAttribute("images", imageService.getImages());
			return "manufacturer-update";
		}
		manufacturerService.updateManufacturer(manufacturer,manufacturerID);
		return "redirect:/manufacturers";
	}

	@GetMapping("/deleteManufacturer")
	public String deleteManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		manufacturerService.deleteManufacturer(manufacturerID);
		return "redirect:/manufacturers";
	}
}

