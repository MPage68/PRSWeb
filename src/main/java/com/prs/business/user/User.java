package com.prs.business.user;

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
	private String passWord;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String eMail;
	private boolean isReviewer;
	private boolean isAdmin;

	public User() {
		super();
	}

	public User(int iD, String userName, String passWord, String firstName, String lastName, String phoneNumber,
			String eMail, boolean isReviewer, boolean isAdmin) {
		super();
		ID = iD;
		this.userName = userName;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.isReviewer = isReviewer;
		this.isAdmin = isAdmin;
	}

	public User(String string, String string2, String string3, String string4, String string5, String string6,
			boolean b, boolean c) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", userName=" + userName + ", passWord=" + passWord + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phonNumber=" + phoneNumber + ", eMail=" + eMail + ", isReviewer="
				+ isReviewer + ", isAdmin=" + isAdmin + "]";
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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
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

	public String getPhonNumber() {
		return phoneNumber;
	}

	public void setPhonNumber(String phonNumber) {
		this.phoneNumber = phonNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean getIsReviewer() {
		return isReviewer;
	}

	public void setIsReviewer(boolean isReviewer) {
		this.isReviewer = isReviewer;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
