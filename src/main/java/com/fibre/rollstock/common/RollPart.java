package com.fibre.rollstock.common;

public enum RollPart {

	Body(1),
	Cover(2),
	Pad(3);
	
	private int id;
	
	private RollPart(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
