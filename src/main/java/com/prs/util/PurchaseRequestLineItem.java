package com.prs.util;

import java.text.DateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.purchaserequest.PurchaseRequest;

@Entity
public class PurchaseRequestLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	@ManyToOne
	@JoinColumn(name = "purchaseRequestID")
	private PurchaseRequest purchaseRequest;
	private int productID;
	private int quantity;
	private boolean isActive;
	private DateFormat dateCreated;
	private DateFormat dateUpdated;
	private boolean updatedByUser;

	public PurchaseRequestLineItem(PurchaseRequest purchaseRequest, int productID, int quantity, boolean isActive,
			DateFormat dateCreated, DateFormat dateUpdated, boolean updatedByUser) {
		super();
		this.purchaseRequest = purchaseRequest;
		this.productID = productID;
		this.quantity = quantity;
		this.isActive = isActive;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.updatedByUser = updatedByUser;
	}

	public PurchaseRequestLineItem(int iD, PurchaseRequest purchaseRequest, int productID, int quantity,
			boolean isActive, DateFormat dateCreated, DateFormat dateUpdated, boolean updatedByUser) {
		super();
		ID = iD;
		this.purchaseRequest = purchaseRequest;
		this.productID = productID;
		this.quantity = quantity;
		this.isActive = isActive;
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

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequestID(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public DateFormat getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(DateFormat dateCreated) {
		this.dateCreated = dateCreated;
	}

	public DateFormat getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(DateFormat dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public boolean isUpdatedByUser() {
		return updatedByUser;
	}

	public void setUpdatedByUser(boolean updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	@Override
	public String toString() {
		return "PurchaseRequestLineItem [ID=" + ID + ", purchaseRequest=" + purchaseRequest + ", productID=" + productID
				+ ", quantity=" + quantity + ", isActive=" + isActive + ", dateCreated=" + dateCreated
				+ ", dateUpdated=" + dateUpdated + ", updatedByUser=" + updatedByUser + "]";
	}

}
