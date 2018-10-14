package com.prs.purchaserequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.user.User;

@Entity
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private LocalDateTime submittedDate;
	

	public PurchaseRequest() {
		ID = 0;
		user = null;
		description = "";
		justification = "";
		dateNeeded = dateNeeded.now();
		deliveryMode = "";
		status = "";
		total = 0.0;
		submittedDate = submittedDate.now();
	}

	public PurchaseRequest(User user, String description, String justification, LocalDate dateNeeded,
			String deliveryMode, String status, double total, LocalDateTime submittedDate) {
		super();
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
	}

	public PurchaseRequest(int iD, User user, String description, String justification, LocalDate dateNeeded,
			String deliveryMode, String status, double total, LocalDateTime submittedDate) {
		super();
		ID = iD;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}

	@Override
	public String toString() {
		return "PurchaseRequest [ID=" + ID + ", user=" + user + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status
				+ ", total=" + total + ", submittedDate=" + submittedDate + "]";
	}
}