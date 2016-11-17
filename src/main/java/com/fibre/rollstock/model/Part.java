package com.fibre.rollstock.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Part implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	
	public Part() {}
	
	public Part(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Part(String name) {
		super();
		this.name = name;
	}
}
