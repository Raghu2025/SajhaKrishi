package com.SajhaKrishi.model;

public class Base {
	private int id;
	private String status;
	private String created_at;
	public Base(int id, String status) {
		this.id = id;
		this.status = status;
	}
	public Base(String status) {
		this.id = id;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
