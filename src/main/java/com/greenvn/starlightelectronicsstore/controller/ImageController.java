package com.greenvn.starlightelectronicsstore.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.service.ImageService;
import com.greenvn.starlightelectronicsstore.service.StorageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private StorageService storageService;
	
	@GetMapping("/images")
	public String showImageList(Model model)
	{
		model.addAttribute("images",imageService.getImages());
		return "";
	}
	
	@GetMapping("/formAddImage")
	public String addImageForm(Image image, HttpServletRequest request, Model model) {

		String uploadRootPath = request.getServletContext().getRealPath("upload");
		model.addAttribute("files", storageService.loadAll(uploadRootPath));
		return "image-upload";
	}
	
	@PostMapping("/addImage")
	public String addImage(@Valid Image image, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "";
		}
		imageService.addImage(image);
		return "";
	}

	@PostMapping("/upload")
	public String handleFileUpload(HttpServletRequest request, @RequestParam("file") List<MultipartFile> file,
			Model model) {
		
		String uploadRootPath = request.getServletContext().getRealPath("upload");

		storageService.storeImageMultiFiles(file, uploadRootPath, null);
		
		
		model.addAttribute("messages", "Đã tải lên thành công!");
		return "image-upload";
	}
	
	@GetMapping("/formUpdateImage")
	public String updateImageForm(@RequestParam(name = "imageID")Long imageID, Model model) {
		Image image = imageService.findImageById(imageID);
		model.addAttribute("image", image);
		return "";
	}
	
	@PostMapping("/updateImage")
	public String updateImage(@RequestParam(name = "imageID")Long imageID,@Valid Image image, BindingResult result, Model model){
		if(result.hasErrors()) {
			image.setImageID(imageID);
			return "";
		}
		imageService.updateImage(image, imageID);
		return "";
	}
	
	@GetMapping("/deleteImage")
	public String deleteImage(@RequestParam(name = "imageID")Long imageID, Model model, Long productID) {
		imageService.deleteImage(imageID);
		return "redirect:/admin/formUpdateProduct?productID="+productID;
	}
}
