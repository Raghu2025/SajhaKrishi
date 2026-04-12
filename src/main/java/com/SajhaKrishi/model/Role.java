package com.SajhaKrishi.model;

public class Role extends Base{
	private String name;
	
	public Role(int id) {
		super(id, "A");
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
