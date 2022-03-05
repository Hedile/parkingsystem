package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;
	private boolean discount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingSpot getParkingSpot() {
		// return parkingSpot;
		return (ParkingSpot) parkingSpot.clone();
	}

	public void setParkingSpot(ParkingSpot parkingSpot) {
		// this.parkingSpot = parkingSpot;
		this.parkingSpot = (ParkingSpot) parkingSpot.clone();
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getInTime() {
		// return inTime;
		return inTime == null ? null : (Date) inTime.clone();
	}

	public void setInTime(Date inTime) {
		// this.inTime = inTime;
		if (inTime == null) {
			this.inTime = null;
		} else {
			this.inTime = (Date) inTime.clone();
		}
	}

	public Date getOutTime() {
		// return outTime;
		return outTime == null ? null : (Date) outTime.clone();
	}

	public void setOutTime(Date outTime) {
		// this.outTime = outTime;
		if (outTime == null) {
			this.outTime = null;
		} else {
			this.outTime = (Date) outTime.clone();
		}
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
}
