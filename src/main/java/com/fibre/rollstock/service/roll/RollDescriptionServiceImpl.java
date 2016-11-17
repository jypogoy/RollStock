package com.fibre.rollstock.service.roll;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.roll.RollDescriptionDAO;
import com.fibre.rollstock.model.RollDescription;

@Service
@Configurable
public class RollDescriptionServiceImpl implements RollDescriptionService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RollDescriptionServiceImpl.class);
	
	@Autowired
	private RollDescriptionDAO descriptionDao;

	@Override
	public long create(RollDescription description) {
		
		long generatedKey = descriptionDao.create(description);
		
		LOGGER.info("Roll Description record '" + description.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(RollDescription description) {
		
		descriptionDao.update(description);
		
		LOGGER.info("Roll Description record '" + description.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		descriptionDao.delete(id);
		
		LOGGER.info("Roll Description record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<RollDescription> findAll() {
		return descriptionDao.findAll();
	}
	
	@Override
	public int count() {
		return descriptionDao.count();
	}
}
