package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
import com.greenvn.starlightelectronicsstore.entities.Category;
import com.greenvn.starlightelectronicsstore.repository.AttributeTypeRepository;

@Service
public class AttributeTypeService {

	@Autowired
	private AttributeTypeRepository attributeTypeRepository;
	
	public List<AttributeType> getAttributeTypes()
	{
		return attributeTypeRepository.findAll();
	}
	
	public AttributeType addAttributeType(AttributeType attributeType)
	{
		AttributeType attributeTypeSaved = attributeTypeRepository.save(attributeType);
		return attributeTypeSaved;
	}
	
	public AttributeType findAttributeTypeById(Long attributeTypeID)
	{
		return attributeTypeRepository.findById(attributeTypeID).get();
	}
	
	public AttributeType updateAttributeType(AttributeType attributeTypeNew, Long attributeTypeID)
	{
		AttributeType attributeType = findAttributeTypeById(attributeTypeID);
		attributeType.setName(attributeTypeNew.getName());
		return attributeTypeRepository.save(attributeType);
	}
	
	public void deleteAttributeType(Long attributeTypeID)
	{
		attributeTypeRepository.deleteById(attributeTypeID);
	}
	

    //Pageable
public Page<AttributeType> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
		
		//sort
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
				Sort.by(sortField).ascending() :
				Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
		Page< AttributeType> pageAttributeType = attributeTypeRepository.findAll(pageable);
		return pageAttributeType;
}
}
