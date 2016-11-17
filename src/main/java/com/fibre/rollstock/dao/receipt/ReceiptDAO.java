package com.fibre.rollstock.dao.receipt;

import java.util.List;

import com.fibre.rollstock.model.Receipt;

/**
 * This interface declares all known activities in handling receipt records.
 * @author jeffrey.pogoy
 *
 */
public interface ReceiptDAO {

	/**
	 * Creates a new receipt record. 
	 * @param receipt
	 * @return id of the newly created record
	 */
	public long create(Receipt receipt);
	
	/**
	 * Modifies an existing receipt record.
	 * @param receipt
	 */
	public void update(Receipt receipt);
	
	/**
	 * Removes a receipt record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves receipt record using a specific id.
	 * @param id
	 * @return receipt record
	 */
	public Receipt findById(long id);
	
	/**
	 * Retrieves receipt records.
	 * @return list of receipt records
	 */
	public List<Receipt> findAll();
	
}
