package com.greenvn.starlightelectronicsstore.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenvn.starlightelectronicsstore.entities.Image;
import com.greenvn.starlightelectronicsstore.entities.Product;

@Service
public class StorageService {

	@Autowired
	private ImageService imageService;

	@Autowired
	private ProductService productService;
	
	public File storeImage(MultipartFile fileData, String uploadRootPath) {
		String name = fileData.getOriginalFilename();
		File uploadRootDir = new File(uploadRootPath);
		if(!uploadRootDir.exists()) {
			uploadRootDir.mkdir();
		}
		if (name != null && name.length() > 0) {
			try {
				// Tạo file tại Server.
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(fileData.getBytes());
				stream.close();
				//
				
				return serverFile;
			} catch (Exception e) {
				System.out.println("Lỗi ghi file: " + name);
				e.printStackTrace();
			}
		}
		return null;
	}
	

	public List<File> storeImageMultiFiles(List<MultipartFile> fileDataList, String uploadRootPath, Product product) {
		List<File> results = new ArrayList<File>();
		List<Image> imageList = new ArrayList<Image>();
		for(MultipartFile file: fileDataList) {
			File saveFile = this.storeImage(file, uploadRootPath);
			results.add(saveFile);
			if(saveFile != null) {
				String name = file.getOriginalFilename();
				Image image = new Image();
				image.setImageURL(uploadRootPath);
				image.setName(name);
				image.setProduct(product);
				imageList.add(imageService.addImage(image));
			}
		}

		if(product != null) productService.updateProductImage(imageList, product.getProductID());
		return results;
	}

	public List<String> loadAll(String rootPath) {
		List<String> filePaths = new ArrayList<>();
		System.out.println("Root: " + rootPath);
		File rootDir = new File(rootPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		File[] files = rootDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				filePaths.add(files[i].getName());
			}
		}
		return filePaths;
	}

	public File loadAsResource(String filename, String uploadRootPath) {
		File uploadRootDir = new File(uploadRootPath);
		return new File(uploadRootDir.getAbsolutePath() + File.separator + filename);
	}
}
