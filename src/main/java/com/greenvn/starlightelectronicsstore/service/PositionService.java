package com.greenvn.starlightelectronicsstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenvn.starlightelectronicsstore.entities.Position;
import com.greenvn.starlightelectronicsstore.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;
	
	public List<Position> getPositions()
	{
		return positionRepository.findAll();
	}
	public List<String> getAllPosition(){
		List<Position>positions= positionRepository.findAll();
		List<String>positionList = new ArrayList<String>();
		for(Position position:positions) {
			positionList.add(position.getName());
		}
		return positionList;
	}
	
	public Position addPosition(Position position)
	{
		Position positionSaved = positionRepository.save(position);
		return positionSaved;
	}
	
	public Position findPositionById(Long positionID)
	{
		return positionRepository.findById(positionID).get();
	}
	
	public Position updatePosition(Position positionNew, Long positionID)
	{
		Position position = findPositionById(positionID);
		position.setName(positionNew.getName());
		position.setEditData(positionNew.getEditData());
		return positionRepository.save(position);
	}
	
	public void deletePosition(Long positionID)
	{
		positionRepository.deleteById(positionID);
	}
}
