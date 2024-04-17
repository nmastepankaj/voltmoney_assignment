package com.voltmoney.pankaj.voltmoneyassignment.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "appointments")
public class Appointment {
	/* Appointment Model for scheduling all the appointmens */
	/* Create only Appointment model according to use case */
	/* No need to create Operator model as it is not required in the use case but can be scaled in future */
	/* No need to create User model as it is not required in the use case because it is only made for one user but can be scaled in future */

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

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", operatorId=" + operatorId + ", scheduledTime=" + scheduledTime + ", booked_date="+ bookedDate+ ", booked=" + booked + "]";
	}
}