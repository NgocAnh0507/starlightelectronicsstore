package com.greenvn.starlightelectronicsstore.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	public File store(MultipartFile fileData, String uploadRootPath) {
		String name = fileData.getOriginalFilename();
		File uploadRootDir = new File(uploadRootPath);
		if(!uploadRootDir.exists()) {
			uploadRootDir.mkdir();
		}
		System.out.println("Client File Name = " + name);
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
