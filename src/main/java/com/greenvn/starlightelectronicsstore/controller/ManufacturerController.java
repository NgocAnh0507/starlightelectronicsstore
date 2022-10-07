package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
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

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;

@Controller
@RequestMapping(value = "/admin")
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private StorageService storageService;
	
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
		return "manufacturer-add";
	}
	
	@PostMapping("/addManufacturer")
	public String addManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		if (result.hasErrors()) {
			return "manufacturer-add";
		}
		
		if(manufacturerService.findManufacturerByName(manufacturer.getName()) != null) {
			
			model.addAttribute("messages","Hãng sản xuất đã tồn tại!");
			return "manufacturer-add";
		}
		else model.addAttribute("messages",null);
		
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File saveFile = storageService.storeImage(file, uploadRootPath);

		if(saveFile != null) {
			String name = file.getOriginalFilename();
			Image image = new Image();
			image.setImageURL(uploadRootPath);
			image.setName(name);
			image.setProduct(null);
			manufacturer.setLogo(imageService.addImage(image));
			model.addAttribute("noImage",null);
		}
		else{
			model.addAttribute("noImage","Ảnh biểu tượng không được để trống!");
			return "manufacturer-add";
		}
		
		manufacturerService.addManufacturer(manufacturer);
		return "redirect:/admin/manufacturers";
	}

	@GetMapping("/formUpdateManufacturer")
	public String updateManufacturerForm(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		Manufacturer manufacturer =  manufacturerService.findManufacturertById(manufacturerID);
		model.addAttribute("manufacturer", manufacturer);
		return "manufacturer-update";
	}

	@PostMapping("/updateManufacturer")
	public String updateManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID,@Valid Manufacturer manufacturer, BindingResult result, Model model,
			HttpServletRequest request, @RequestParam("file") MultipartFile file){
		if(result.hasErrors()) {
			model.addAttribute("manufacturer", manufacturer);
			return "manufacturer-update";
		}
		
		Manufacturer M = manufacturerService.findManufacturerByName(manufacturer.getName());
		if(M != null && M.getManufacturerID() != manufacturer.getManufacturerID()) {
			
			model.addAttribute("messages","Hãng sản xuất đã tồn tại!");
			return "manufacturer-add";
		}
		else model.addAttribute("messages",null);
		
		Image currentImage = manufacturerService.findManufacturertById(manufacturerID).getLogo();
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		File saveFile = storageService.storeImage(file, uploadRootPath);
		
		if(saveFile != null) {
			String name = file.getOriginalFilename();
			Image image = new Image();
			image.setImageURL(uploadRootPath);
			image.setName(name);
			image.setProduct(null);
			manufacturer.setLogo(imageService.addImage(image));
		}
		else manufacturer.setLogo(currentImage);
		
		manufacturerService.updateManufacturer(manufacturer,manufacturerID);
		if(saveFile != null) imageService.deleteImage(currentImage.getImageID());
		return "redirect:/admin/manufacturers";
	}

	@GetMapping("/deleteManufacturer")
	public String deleteManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model) {
		Manufacturer M = manufacturerService.findManufacturertById(manufacturerID);
		manufacturerService.deleteManufacturer(manufacturerID);
		imageService.deleteImage(M.getLogo().getImageID());
		return "redirect:/admin/manufacturers";
	}
}

