package com.prs.business.vendor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int ID;
	private String code;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	private String email;
	private boolean isPreApproved;

	public Vendor() {
		super();
	}

	public Vendor(int iD, String code, String name, String address, String city, String state, String zip,
			String phoneNumber, String email, boolean isPreApproved) {
		super();
		ID = iD;
		this.code = code;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isPreApproved = isPreApproved;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPreApproved() {
		return isPreApproved;
	}

	public void setPreApproved(boolean isPreApproved) {
		this.isPreApproved = isPreApproved;
	}

}
