package com.fibre.rollstock.service.supplier;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.supplier.SupplierDAO;
import com.fibre.rollstock.model.Supplier;

@Service
@Configurable
public class SupplierServiceImpl implements SupplierService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);
	
	@Autowired
	private SupplierDAO supplierDao;
	
	@Override
	public long create(Supplier supplier) {
		
		long generatedKey = supplierDao.create(supplier);
		
		LOGGER.info("Supplier record '" + supplier.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Supplier supplier) {
		
		supplierDao.update(supplier);
		
		LOGGER.info("Supplier record '" + supplier.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		supplierDao.delete(id);
		
		LOGGER.info("Supplier record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Supplier> findAll() {
		return supplierDao.findAll();
	}

	@Override
	public int count() {
		return supplierDao.count();
	}
}
