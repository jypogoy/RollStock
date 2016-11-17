package com.fibre.rollstock.dao.ticket;

import java.util.List;

import com.fibre.rollstock.model.Ticket;

/**
 * This interface declares all known activities in handling ticket records.
 * @author jeffrey.pogoy
 *
 */
public interface TicketDAO {

	/**
	 * Creates a new ticket record. 
	 * @return id of the newly created record
	 */
	public long create();
	
	/**
	 * Modifies an existing ticket record.
	 * @param ticket
	 */
	public void update(Ticket ticket);
	
	/**
	 * Removes a ticket record using a particular id.
	 * @param id
	 */
	public void delete(long id);
	
	/**
	 * Retrieves ticket record using a specific id.
	 * @param id
	 * @return ticket record
	 */
	public Ticket findById(long id);
	
	/**
	 * Retrieves ticket records.
	 * @return list of ticket records
	 */
	public List<Ticket> findAll();
}
