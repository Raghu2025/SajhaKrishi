package com.SajhaKrishi.model;

import org.mindrot.jbcrypt.BCrypt;

public class User extends Base {
	private String fullName;
	private String phoneNumber;
	private String email;
	private String password;
	private String address;
	private String district;
	private int roleId;
	private String roleName;

	public User(int id, String fullName,String email, String password, String address, String district, int role, String phoneNumber) {
		super(id, "A");
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.address = address;
		this.district = district;
		this.roleId = role;
	}

	public User(String fullName,String email, String password, String address, String district, int role, String phoneNumber) {
		super("A");
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.district = district;
		this.roleId = role;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean matchPassword(String inputPassword) {
		return BCrypt.checkpw(inputPassword, this.password);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int role) {
		this.roleId = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



}
