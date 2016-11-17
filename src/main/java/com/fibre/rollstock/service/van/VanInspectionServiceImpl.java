package com.fibre.rollstock.service.van;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.van.VanInspectionDAO;
import com.fibre.rollstock.model.VanInspection;

@Service
@Configurable
public class VanInspectionServiceImpl implements VanInspectionService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VanInspectionServiceImpl.class);
	
	@Autowired
	private VanInspectionDAO vanInspectionDao;

	@Override
	public long create(VanInspection vanInspection) {
		
		long generatedKey = vanInspectionDao.create(vanInspection);
		
		LOGGER.info("Van's inspection record '" + vanInspection.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(VanInspection vanInspection) {
		
		vanInspectionDao.update(vanInspection);
		
		LOGGER.info("Van's inspection record '" + vanInspection.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		vanInspectionDao.delete(id);
		
		LOGGER.info("Van's inspection record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<VanInspection> findAll() {
		return vanInspectionDao.findAll();
	}
	
	@Override
	public int count() {
		return vanInspectionDao.count();
	}
}
