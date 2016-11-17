package com.fibre.rollstock.service.storage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.storage.StorageDAO;
import com.fibre.rollstock.model.Storage;

@Service
@Configurable
public class StorageServiceImpl implements StorageService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	@Autowired
	private StorageDAO storageDao;

	@Override
	public long create(Storage storage) {
		
		long generatedKey = storageDao.create(storage);
		
		LOGGER.info("Storage record '" + storage.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Storage storage) {
		
		storageDao.update(storage);
		
		LOGGER.info("Storage record '" + storage.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		storageDao.delete(id);
		
		LOGGER.info("Storage record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Storage> findAll() {
		return storageDao.findAll();
	}
	
	@Override
	public int count() {
		return storageDao.count();
	}
}
