package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
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

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.entities.ProductAttribute;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.ManufacturerService;
import com.greenvn.starlightelectronicsstore.service.StorageService;

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
			Model model,HttpServletRequest request,
			@RequestParam(name= "notice",required = false)String notice)
	{

		if(model != null )model.addAttribute("notice", notice);
		
		int pageSize = 9;
		Page<Manufacturer> pageManufacturer = manufacturerService.findAll(pageNo, pageSize,sortField,sortDir);
		List<Manufacturer> manufacturers = pageManufacturer.getContent();
		if(manufacturers.size() == 0) manufacturers = null;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPage", pageManufacturer.getTotalPages());
		HttpSession session = request.getSession();
		session.setAttribute("menuSelected","manufacturers" );
		
		//sort
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("manufacturers",manufacturers);
		return "manufacturer-management";
				
	}
	
	@GetMapping("/formAddManufacturer")
	public String addManufacturerForm(Manufacturer manufacturer, Model model,
			@RequestParam(name = "callFrom")String callFrom,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		model.addAttribute("callFrom", callFrom);
		model.addAttribute("callFromID", callFromID);
		return "manufacturer-add";
	}
	
	@PostMapping("/addManufacturer")
	public String addManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model,
			@RequestParam(name = "callFrom")String callFrom, HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam(name= "callFromID",required = false)Long callFromID) {
		if (result.hasErrors()) {
			model.addAttribute("notice", "Th??m h??ng s???n xu???t th???t b???i!");
			model.addAttribute("callFrom", callFrom);
			model.addAttribute("callFromID", callFromID);
			return "manufacturer-add";
		}
		
		if(manufacturerService.findManufacturerByName(manufacturer.getName()) != null) {

			model.addAttribute("notice", "Th??m h??ng s???n xu???t th???t b???i!");
			model.addAttribute("messages","H??ng s???n xu???t ???? t???n t???i!");
			model.addAttribute("callFrom", callFrom);
			model.addAttribute("callFromID", callFromID);
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
			model.addAttribute("noImage","???nh bi???u t?????ng kh??ng ???????c ????? tr???ng!");
			return "manufacturer-add";
		}
		
		manufacturerService.addManufacturer(manufacturer);
		String notice = "addManufacturer";
		if(callFromID != null) return "redirect:" + callFrom + callFromID + "&notice=" + notice;
		return "redirect:" + callFrom + "?notice=" + notice;
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
			Image currentImage = manufacturerService.findManufacturertById(manufacturerID).getLogo();
			manufacturer.setLogo(currentImage);
			model.addAttribute("notice", "Ch???nh s???a h??ng s???n xu???t th???t b???i");
			model.addAttribute("manufacturer", manufacturer);
			return "manufacturer-update";
		}
		
		Manufacturer M = manufacturerService.findManufacturerByName(manufacturer.getName());
		if(M != null && M.getManufacturerID() != manufacturer.getManufacturerID()) {

			model.addAttribute("notice", "Ch???nh s???a h??ng s???n xu???t th???t b???i");
			model.addAttribute("messages","H??ng s???n xu???t ???? t???n t???i!");
			return "manufacturer-update";
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
		return showManufacturerList(1,"manufacturerID","asc",model,request,"Ch???nh s???a h??ng s???n xu???t th??nh c??ng!");
	}

	@GetMapping("/deleteManufacturer")
	public String deleteManufacturer(@RequestParam(name = "manufacturerID")Long manufacturerID, Model model,HttpServletRequest request) {
		Manufacturer M = manufacturerService.findManufacturertById(manufacturerID);
		if(M == null) return "redirect:/admin/manufacturers";
		if(M.getProducts().size() > 0) {
			model.addAttribute("messages","Kh??ng th??? x??a h??ng s???n xu???t ??ang c?? s???n ph???m!");
			return showManufacturerList(1,"manufacturerID","asc",model,request,"X??a h??ng s???n xu???t th???t b???i!");
		}
		manufacturerService.deleteManufacturer(manufacturerID);
		imageService.deleteImage(M.getLogo().getImageID());
		return showManufacturerList(1,"manufacturerID","asc",model,request,"X??a h??ng s???n xu???t th??nh c??ng!");
	}
}

