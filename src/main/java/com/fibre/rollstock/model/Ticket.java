package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Ticket implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Timestamp dateCreated;
	
	public Ticket() {}
	
	public Ticket(long id, Timestamp dateCreated) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
	}
	
	public Ticket(Timestamp dateCreated) {
		super();
		this.dateCreated = dateCreated;
	}

}
