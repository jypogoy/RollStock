package com.fibre.rollstock.dao.storage;

import java.util.List;

import com.fibre.rollstock.model.Storage;

/**
 * This interface declares all known activities in handling storage records.
 * @author jeffrey.pogoy
 *
 */
public interface StorageDAO {

	/**
	 * Creates a new storage record. 
	 * @param storage
	 * @return id of the newly created record
	 */
	public long create(Storage storage);
	
	/**
	 * Modifies an existing storage record.
	 * @param storage
	 */
	public void update(Storage storage);
	
	/**
	 * Removes a storage record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves storage records. May provide filtered values using a keyword.
	 * @return list of storage records
	 */
	public List<Storage> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
