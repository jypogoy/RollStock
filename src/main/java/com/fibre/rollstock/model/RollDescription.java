package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class RollDescription implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String text;
	private String details;
	private Timestamp dateCreated;
	
	public RollDescription() {}
	
	public RollDescription(long id, String text, String details, Timestamp dateCreated) {
		super();
		this.id = id;
		this.text = text;
		this.details = details;
		this.dateCreated = dateCreated;
	}
	
	public RollDescription(String text, String details, Timestamp dateCreated) {
		super();
		this.text = text;
		this.details = details;
		this.dateCreated = dateCreated;
	}
}
