package com.prs.purchaserequest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.print.attribute.standard.DateTimeAtProcessing;

import com.prs.business.user.User;
@Entity
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID; 
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;
	private String description;
	private String justification;
	private Date dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private DateTimeAtCreation submittedDate;
	private boolean isActive; 
	private String reasonForRejection;
	private DateTimeAtCreation dateCreated;
	private DateTimeAtProcessing dateUpdated;
	private DateTimeAtProcessing updatedByUser;
	
	

	public PurchaseRequest(User user, String description, String justification, Date dateNeeded, String deliveryMode,
			String status, double total, DateTimeAtCreation submittedDate, boolean isActive, String reasonForRejection,
			DateTimeAtCreation dateCreated, DateTimeAtProcessing dateUpdated, DateTimeAtProcessing updatedByUser) {
		super();
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.isActive = isActive;
		this.reasonForRejection = reasonForRejection;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.updatedByUser = updatedByUser;
	}

	public PurchaseRequest(int iD, User user, String description, String justification, Date dateNeeded,
			String deliveryMode, String status, double total, DateTimeAtCreation submittedDate, boolean isActive,
			String reasonForRejection, DateTimeAtCreation dateCreated, DateTimeAtProcessing dateUpdated,
			DateTimeAtProcessing updatedByUser) {
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
		this.isActive = isActive;
		this.reasonForRejection = reasonForRejection;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.updatedByUser = updatedByUser;
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

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(Date dateNeeded) {
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

	public DateTimeAtCreation getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(DateTimeAtCreation submittedDate) {
		this.submittedDate = submittedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	public DateTimeAtCreation getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(DateTimeAtCreation dateCreated) {
		this.dateCreated = dateCreated;
	}

	public DateTimeAtProcessing getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(DateTimeAtProcessing dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public DateTimeAtProcessing getUpdatedByUser() {
		return updatedByUser;
	}

	public void setUpdatedByUser(DateTimeAtProcessing updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	@Override
	public String toString() {
		return "PurchaseRequest [ID=" + ID + ", user=" + user + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status
				+ ", total=" + total + ", submittedDate=" + submittedDate + ", isActive=" + isActive
				+ ", reasonForRejection=" + reasonForRejection + ", dateCreated=" + dateCreated + ", dateUpdated="
				+ dateUpdated + ", updatedByUser=" + updatedByUser + "]";
	}

}
