package com.greenvn.starlightelectronicsstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.service.ImageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping("/images")
	public String showImageList(Model model)
	{
		model.addAttribute("images",imageService.getImages());
		return "/images";
	}
	
	@GetMapping("/formAddImage")
	public String addImageForm(Image image) {
		return "add-image";
	}
	
	@PostMapping("/addImage")
	public String addImage(@Valid Image image, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-image";
		}
		imageService.addImage(image);
		return "";
	}
	
	@GetMapping("/formUpdateImage")
	public String updateImageForm(@RequestParam(name = "imageID")Long imageID, Model model) {
		Image image = imageService.findImageById(imageID);
		model.addAttribute("image", image);
		return "update-image";
	}
	
	@PostMapping("/updateImage")
	public String updateImage(@RequestParam(name = "imageID")Long imageID,@Valid Image image, BindingResult result, Model model){
		if(result.hasErrors()) {
			image.setImageID(imageID);
			return "update-image";
		}
		imageService.updateImage(image, imageID);
		return "";
	}
	
	@GetMapping("/deleteImage")
	public String deleteImage(@RequestParam(name = "imageID")Long imageID, Model model) {
		imageService.deleteImage(imageID);
		return "";
	}
}
