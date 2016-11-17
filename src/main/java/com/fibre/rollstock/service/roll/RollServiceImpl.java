package com.fibre.rollstock.service.roll;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.roll.RollDAO;
import com.fibre.rollstock.model.Roll;

@Service
@Configurable
public class RollServiceImpl implements RollService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RollServiceImpl.class);
	
	@Autowired
	private RollDAO rollDao;

	@Override
	public long create(Roll roll) {
		
		long generatedKey = rollDao.create(roll);
		
		LOGGER.info("Roll record '" + roll.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Roll roll) {
		
		rollDao.update(roll);
		
		LOGGER.info("Roll record '" + roll.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		rollDao.delete(id);
		
		LOGGER.info("Roll record [" + id + "] was successfully deleted.");
	}

	@Override
	public List<Roll> findAll() {
		return rollDao.findAll();
	}

	@Override
	public List<Roll> findByReceipt(long receiptId) {
		return rollDao.findByReceipt(receiptId);
	}

	@Override
	public void createMultiple(List<Roll> rolls) {
		
		rollDao.createMultiple(rolls);
		
		LOGGER.info("Successfully created " + rolls.size() + " Roll record " + (rolls.size() > 1 ? "s" : "") + ".");
	}

	@Override
	public void deleteByReceipt(long receiptId) {
		
		rollDao.deleteByReceipt(receiptId);
		
		LOGGER.info("Successfully deleted roll records of receipt " + receiptId);
	}
	
	@Override
	public List<Roll> findForStorage() {
		return rollDao.findForStorage();
	}

	@Override
	public List<Roll> findByBin(long binId) {
		return rollDao.findByBin(binId);
	}
}
