package com.voltmoney.pankaj.voltmoneyassignment.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "operatorId")
	private int operatorId;

	@Column(name = "scheduledTime")
	private int scheduledTime;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "bookedDate")
    private Date bookedDate;
    

	@Column(name = "booked")
	private boolean booked;

	public Appointment() {

	}

	public Appointment(int operatorId, int scheduledTime, boolean booked) {
		this.operatorId = operatorId;
		this.scheduledTime = scheduledTime;
		this.booked = booked;
	}

	public long getId() {
		return id;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public int getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(int scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean isBooked) {
		this.booked = isBooked;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", operatorId=" + operatorId + ", scheduledTime=" + scheduledTime + ", booked_date="+ bookedDate+ ", booked=" + booked + "]";
	}
}