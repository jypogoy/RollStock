package com.fibre.rollstock.service.worker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.worker.WorkerDAO;
import com.fibre.rollstock.model.Worker;

@Service
@Configurable
public class WorkerServiceImpl implements WorkerService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(WorkerServiceImpl.class);
	
	@Autowired
	private WorkerDAO workerDao;
	
	@Override
	public long create(Worker worker) {
		
		long generatedKey = workerDao.create(worker);
		
		LOGGER.info("Worker record '" + worker.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Worker worker) {
		
		workerDao.update(worker);
		
		LOGGER.info("Worker record '" + worker.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		workerDao.delete(id);
		
		LOGGER.info("Worker record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Worker> findAll() {
		return workerDao.findAll();
	}

	@Override
	public int count() {
		return workerDao.count();
	}
}
