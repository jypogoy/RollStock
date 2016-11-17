package com.fibre.rollstock.service.van;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.van.VanImageDAO;
import com.fibre.rollstock.model.VanImage;

@Service
@Configurable
public class VanImageServiceImpl implements VanImageService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(VanImageServiceImpl.class);
	
	@Autowired
	private VanImageDAO vanImageDao;

	@Override
	public long create(VanImage vanImage) {
		
		long generatedKey = vanImageDao.create(vanImage);
		
		LOGGER.info("Van's image record '" + vanImage.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(VanImage vanImage) {
		
		vanImageDao.update(vanImage);
		
		LOGGER.info("Van's image record '" + vanImage.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		vanImageDao.delete(id);
		
		LOGGER.info("Van's image record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<VanImage> findAll() {
		return vanImageDao.findAll();
	}
	
	@Override
	public int count() {
		return vanImageDao.count();
	}
}
