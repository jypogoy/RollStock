package com.fibre.rollstock.service.receipt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.receipt.ReceiptDAO;
import com.fibre.rollstock.model.Receipt;

@Service
@Configurable
public class ReceiptServiceImpl implements ReceiptService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReceiptServiceImpl.class);
	
	@Autowired
	private ReceiptDAO receiptDao;

	@Override
	public long create(Receipt receipt) {
		
		long generatedKey = receiptDao.create(receipt);
		
		LOGGER.info("Receipt record '" + receipt.toString() + "' was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Receipt receipt) {
		
		receiptDao.update(receipt);
		
		LOGGER.info("Receipt record '" + receipt.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		receiptDao.delete(id);
		
		LOGGER.info("Receipt record [" + id + "] was successfully deleted.");
	}

	@Override
	public Receipt findById(long id) {
		return receiptDao.findById(id);
	}

	@Override
	public List<Receipt> findAll() {
		return receiptDao.findAll();
	}
}
