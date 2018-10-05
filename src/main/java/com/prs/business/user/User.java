package com.prs.business.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String eMail;
	@Column(name = "isReviewer")
	private boolean reviewer;
	@Column(name = "isAdmin")
	private boolean admin;

	public User() {
		userName = "";
		password = "";
	}

	public User(int iD, String userName, String password, String firstName, String lastName, String phoneNumber,
			String eMail, boolean reviewer, boolean admin) {
		super();
		ID = iD;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.reviewer = reviewer;
		this.admin = admin;
	}

	public User(String userName, String password, String firstName, String lastName, String phoneNumber, String eMail,
			boolean isReviewer, boolean isAdmin) {
		setUserName(userName);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		seteMail(eMail);
		setReviewer(isReviewer);
		setAdmin(isAdmin);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isReviewer() {
		return reviewer;
	}

	public void setReviewer(boolean reviewer) {
		this.reviewer = reviewer;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", eMail=" + eMail + ", reviewer="
				+ reviewer + ", admin=" + admin + "]";
	}

	
}
