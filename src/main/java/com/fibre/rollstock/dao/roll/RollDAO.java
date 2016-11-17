package com.fibre.rollstock.dao.roll;

import java.util.List;

import com.fibre.rollstock.model.Roll;

/**
 * This interface declares all known activities in handling roll records.
 * @author jeffrey.pogoy
 *
 */
public interface RollDAO {

	/**
	 * Creates a new roll record. 
	 * @param roll
	 * @return id of the newly created record
	 */
	public long create(Roll roll);
	
	/**
	 * Modifies an existing roll record.
	 * @param roll
	 */
	public void update(Roll roll);
	
	/**
	 * Removes a roll record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves roll records.
	 * @return list of roll records
	 */
	public List<Roll> findAll();
	
	/**
	 * Retrieves roll records using their related receipt.
	 * @param receiptId
	 * @return list of roll records.
	 */
	public List<Roll> findByReceipt(long receiptId);
	
	/**
	 * Optimized and simultaneous roll record creation. No new IDs will be returned.
	 * @param rolls
	 */
	public void createMultiple(List<Roll> rolls);
	
	/**
	 * Removes all roll records related to s specific receipt.
	 * @param receiptId
	 */
	public void deleteByReceipt(long receiptId);
	
	/**
	 * Retrieves records of rolls that are not yet stored.
	 * @return list of roll records
	 */
	public List<Roll> findForStorage();
	
	/**
	 * Retrieves records of rolls within a specific bin.
	 * @param binId
	 * @return list of roll records
	 */
	public List<Roll> findByBin(long binId);
}
