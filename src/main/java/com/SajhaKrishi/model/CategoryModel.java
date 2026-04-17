package com.SajhaKrishi.model;

public class CategoryModel extends Base {
	private String name;

	public CategoryModel(String name) {
		super("A");
		this.name = name;
	}

	public CategoryModel(int id, String name) {
		super(id, "A");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
