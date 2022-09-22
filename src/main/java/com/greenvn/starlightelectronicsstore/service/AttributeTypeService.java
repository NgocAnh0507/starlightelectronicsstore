package com.greenvn.starlightelectronicsstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.AttributeType;
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
	
	public AttributeType findAttributeTypeById(Long id)
	{
		return attributeTypeRepository.findById(id).get();
	}
	
	public AttributeType updateAttributeType(AttributeType attributeTypeNew, Long id)
	{
		AttributeType attributeType = findAttributeTypeById(id);
		attributeType.setName(attributeTypeNew.getName());
		return attributeTypeRepository.save(attributeType);
	}
	
	public void deleteAttributeType(Long id)
	{
		attributeTypeRepository.deleteById(id);
	}
}
