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
	
	public Position addPosition(Position position)
	{
		Position positionSaved = positionRepository.save(position);
		return positionSaved;
	}
	
	public Position findPositionById(Long id)
	{
		return positionRepository.findById(id).get();
	}
	
	public Position updatePosition(Position positionNew, Long id)
	{
		Position position = findPositionById(id);
		position.setName(positionNew.getName());
		return positionRepository.save(position);
	}
	
	public void deletePosition(Long id)
	{
		positionRepository.deleteById(id);
	}
	public void managePosition(){
		Position posi = new Position();
		posi.setName("MANAGER");
		positionRepository.save(posi);
	}
}
