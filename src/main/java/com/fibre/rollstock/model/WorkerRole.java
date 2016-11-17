package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class WorkerRole implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private Timestamp dateCreated;
	
	public WorkerRole() {}
	
	public WorkerRole(long id, String name, String description, Timestamp dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.dateCreated = dateCreated;
	}
	
	public WorkerRole(String name, String description, Timestamp dateCreated) {
		super();
		this.name = name;
		this.description = description;
		this.dateCreated = dateCreated;
	}
}
