package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Manufacturer;
import com.greenvn.starlightelectronicsstore.repository.ManufacturerRepository;

@Service
public class ManufacturerService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	public List<Manufacturer> getManufacturers()
	{
		return manufacturerRepository.findAll();
	}
	
	public Manufacturer addManufacturer(Manufacturer manufacturer)
	{
		Manufacturer manufacturerSaved = manufacturerRepository.save(manufacturer);
		return manufacturerSaved;
	}
	
	public Manufacturer findManufacturertById(Long id)
	
	{
		return manufacturerRepository.findById(id).get();
	}
	
	public Manufacturer updateManufacturer(Manufacturer manufacturerNew, Long id)
	{
		Manufacturer manufacturer = findManufacturertById(id);
		manufacturer.setName(manufacturerNew.getName());
		manufacturer.setLogo(manufacturerNew.getLogo());
		return manufacturerRepository.save(manufacturer);
	}
	
	public void deleteManufacturer(Long id)
	{
		manufacturerRepository.deleteById(id);
	}
}
