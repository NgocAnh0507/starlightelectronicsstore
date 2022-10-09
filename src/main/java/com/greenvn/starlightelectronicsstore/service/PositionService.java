package com.greenvn.starlightelectronicsstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public Position findPositionByUserName(String name)
	{
		return positionRepository.findPositionByName(name);
	}
	public List<String> getAllPosition(){
		List<Position> positions = positionRepository.findAll();
		List<String> positionList = new ArrayList<String>();
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
	
	public Position findPositionByName(String Name)
	{
		return positionRepository.findPositionByName(Name);
	}
	
	public Position findPositionById(Long positionID)
	{
		return positionRepository.findById(positionID).get();
	}
	
	public Position updatePosition(Position positionNew, Long positionID)
	{
		Position position = findPositionById(positionID);
		position.setName(positionNew.getName());
		position.setRole(positionNew.getRole());
		return positionRepository.save(position);
	}
	
	public void deletePosition(Long positionID)
	{
		positionRepository.deleteById(positionID);
	}
	
	//Pageable
	public Page<Position> findAll(int pageNo, int pageSize,String sortField, String sortDirection){
			
			//sort
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
					Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
			Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
			Page<Position> pagePosition = positionRepository.findAll(pageable);
			return pagePosition;
	}
	
}
