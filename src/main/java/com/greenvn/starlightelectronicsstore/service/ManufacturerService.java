package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public Manufacturer findManufacturertById(Long manufacturerID)
	
	{
		return manufacturerRepository.findById(manufacturerID).get();
	}

	public Manufacturer findManufacturerByName(String Name)
	{
		return manufacturerRepository.findManufacturerByName(Name);
	}
	
	public Manufacturer updateManufacturer(Manufacturer manufacturerNew, Long manufacturerID)
	{
		Manufacturer manufacturer = findManufacturertById(manufacturerID);
		manufacturer.setName(manufacturerNew.getName());
		manufacturer.setLogo(manufacturerNew.getLogo());
		return manufacturerRepository.save(manufacturer);
	}
	
	public void deleteManufacturer(Long manufacturerID)
	{
		manufacturerRepository.deleteById(manufacturerID);
	}
	
    //Pageable
	public Page<Manufacturer> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
			
			//sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
					Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
			
			Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
			Page<Manufacturer> pageManufacturer = manufacturerRepository.findAll(pageable);
			return pageManufacturer;
	}
}
