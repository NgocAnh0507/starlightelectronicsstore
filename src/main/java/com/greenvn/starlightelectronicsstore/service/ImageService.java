package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	public List<Image> getImages()
	{
		return imageRepository.findAll();
	}
	
	public Image addImage(Image image)
	{
		Image imageSaved = imageRepository.save(image);
		return imageSaved;
	}
	
	public Image findImageById(Long imageID)
	{
		if(imageRepository.findById(imageID).isEmpty()) return null;
		return imageRepository.findById(imageID).get();
	}
	
	public Image updateImage(Image imageNew, Long imageID)
	{
		Image image = findImageById(imageID);
		if(image == null) return null;
		image.setName(imageNew.getName());
		image.setImageURL(imageNew.getImageURL());
		image.setProduct(imageNew.getProduct());
		return imageRepository.save(image);
	}
	
	public void deleteImage(Long imageID)
	{
		imageRepository.deleteById(imageID);
	}

}
