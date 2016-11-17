package com.fibre.rollstock.service.warehouse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.warehouse.WarehouseDAO;
import com.fibre.rollstock.model.Warehouse;

@Service
@Configurable
public class WarehouseServiceImpl implements WarehouseService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);
	
	@Autowired
	private WarehouseDAO warehouseDao;

	@Override
	public long create(Warehouse warehouse) {
		
		long generatedKey = warehouseDao.create(warehouse);
		
		LOGGER.info("Warehouse record '" + warehouse.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Warehouse warehouse) {
		
		warehouseDao.update(warehouse);
		
		LOGGER.info("Warehouse record '" + warehouse.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		warehouseDao.delete(id);
		
		LOGGER.info("Warehouse record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Warehouse> findAll() {
		return warehouseDao.findAll();
	}

	@Override
	public Warehouse findById(long id) {
		return warehouseDao.findById(id);
	}
	
	@Override
	public int count() {
		return warehouseDao.count();
	}
}
