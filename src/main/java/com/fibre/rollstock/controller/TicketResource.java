package com.fibre.rollstock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fibre.rollstock.model.Ticket;
import com.fibre.rollstock.service.ticket.TicketService;

@RestController
@RequestMapping("/api_tickets")
public class TicketResource {

	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Ticket> findAll(String keyword) {
		return ticketService.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Ticket findById(@PathVariable long id) {
		return ticketService.findById(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public long create() {
		return ticketService.create();
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	public void update(@Valid @RequestBody Ticket ticket) {
		ticketService.update(ticket);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable long id) {
		ticketService.delete(id);
	}
}
