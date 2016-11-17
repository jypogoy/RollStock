package com.fibre.rollstock.service.trucker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.trucker.TruckerDAO;
import com.fibre.rollstock.model.Trucker;

@Service
@Configurable
public class TruckerServiceImpl implements TruckerService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TruckerServiceImpl.class);
	
	@Autowired
	private TruckerDAO truckerDao;

	@Override
	public long create(Trucker trucker) {
		
		long generatedKey = truckerDao.create(trucker);
		
		LOGGER.info("Trucker record '" + trucker.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Trucker trucker) {
		
		truckerDao.update(trucker);
		
		LOGGER.info("Trucker record '" + trucker.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		truckerDao.delete(id);
		
		LOGGER.info("Trucker record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Trucker> findAll() {
		return truckerDao.findAll();
	}
	
	@Override
	public Trucker findById(long id) {
		return truckerDao.findById(id);
	}
	
	@Override
	public int count() {
		return truckerDao.count();
	}	
}
