package com.fibre.rollstock.service.ticket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.fibre.rollstock.dao.ticket.TicketDAO;
import com.fibre.rollstock.model.Ticket;

@Service
@Configurable
public class TicketServiceImpl implements TicketService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDAO ticketDao;

	@Override
	public long create() {
		
		long generatedKey = ticketDao.create();
		
		LOGGER.info("Ticket record was successfully created.");
	
		return generatedKey;
	}

	@Override
	public void update(Ticket ticket) {
		
		ticketDao.update(ticket);
		
		LOGGER.info("Ticket record '" + ticket.toString() + "' was successfully updated.");
	}
	
	@Override
	public void delete(long id) {
		
		ticketDao.delete(id);
		
		LOGGER.info("Ticket record [" + id + "] was successfully deleted.");
	}

	@Override
	public Ticket findById(long id) {
		return ticketDao.findById(id);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketDao.findAll();
	}
	
}
