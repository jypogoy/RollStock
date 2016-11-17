package com.fibre.rollstock.service.bin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.bin.BinDAO;
import com.fibre.rollstock.model.Bin;

@Service
@Configurable
public class BinServiceImpl implements BinService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BinServiceImpl.class);
	
	@Autowired
	private BinDAO binDao;

	@Override
	public long create(Bin bin) {
		
		long generatedKey = binDao.create(bin);
		
		LOGGER.info("Bin record '" + bin.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Bin bin) {
		
		binDao.update(bin);
		
		LOGGER.info("Bin record '" + bin.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		binDao.delete(id);
		
		LOGGER.info("Bin record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Bin> findAll() {
		return binDao.findAll();
	}

	@Override
	public Bin findById(long binId) {
		return binDao.findById(binId);
	}
	
	@Override
	public List<Bin> findByWarehouse(long warehouseId) {
		return binDao.findByWarehouse(warehouseId);
	}

	@Override
	public List<Bin> findAvailableByWarehouse(long warehouseId) {
		return binDao.findAvailableByWarehouse(warehouseId);
	}
	
	@Override
	public int count() {
		return binDao.count();
	}	
}
