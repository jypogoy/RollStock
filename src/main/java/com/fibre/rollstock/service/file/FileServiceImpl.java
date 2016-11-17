package com.fibre.rollstock.service.file;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.file.FileDAO;
import com.fibre.rollstock.model.ElectronicFile;

@Service
@Configurable
public class FileServiceImpl implements FileService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Autowired
	private FileDAO fileDao;

	@Override
	public long create(ElectronicFile file) {
		
		long generatedKey = fileDao.create(file);
		
		LOGGER.info("File record '" + file.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(ElectronicFile file) {
		
		fileDao.update(file);
		
		LOGGER.info("File record '" + file.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		fileDao.delete(id);
		
		LOGGER.info("File record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<ElectronicFile> findAll() {
		return fileDao.findAll();
	}
	
	@Override
	public int count() {
		return fileDao.count();
	}
}
