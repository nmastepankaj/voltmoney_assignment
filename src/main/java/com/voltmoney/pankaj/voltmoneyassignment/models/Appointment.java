package com.voltmoney.pankaj.voltmoneyassignment.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
	/* Appointment Model for scheduling all the appointmens */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "operatorId")
	private int operatorId;

	// scheduledTime is the time slot for the appointment
	// It is an integer value from 0 to 23
	// It is only start time, end time is start time + 1 ( because each appointment is of 1 hour)
	@Column(name = "scheduledTime")
	private int scheduledTime;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "bookedDate")
    private Date bookedDate;
    
	// Check for wheather the appointment is booked
	// If someone cancels the appointment, then this value will be set to false
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
		return "Appointment [id=" + id + ", operatorId=" + operatorId + ", scheduledTime=" + scheduledTime + ", booked_date="+ bookedDate+ ", booked=" + booked + "]";
	}
}