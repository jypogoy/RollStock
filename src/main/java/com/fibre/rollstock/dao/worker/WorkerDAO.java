package com.fibre.rollstock.dao.worker;

import java.util.List;

import com.fibre.rollstock.model.Worker;

/**
 * This interface declares all known activities in handling worker records.
 * @author jeffrey.pogoy
 *
 */
public interface WorkerDAO {

	/**
	 * Creates a new worker record.
	 * @param worker
	 * @return id of the newly created record
	 */
	public long create(Worker worker);
	
	/**
	 * Modifies an existing worker record.
	 * @param worker
	 */
	public void update(Worker worker);
	
	/**
	 * Removes a worker record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves worker records. May provide filtered values using a keyword.
	 * @return list of worker records
	 */
	public List<Worker> findAll();
	
	/**
	 * Provides the total record.
	 * @return record count
	 */
	public int count();
}
