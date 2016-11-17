package com.fibre.rollstock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ElectronicFile implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String fileName;
	private String base64;
	private Timestamp dateCreated;
	
	public ElectronicFile() {}
	
	public ElectronicFile(long id, String fileName, String base64, Timestamp dateCreated) {
		super();
		this.id = id;
		this.fileName = fileName;		
		this.base64 = base64;
		this.dateCreated = dateCreated;
	}
	
	public ElectronicFile(String fileName, String base64, Timestamp dateCreated) {
		super();
		this.fileName = fileName;		
		this.base64 = base64;
		this.dateCreated = dateCreated;
	}
}
