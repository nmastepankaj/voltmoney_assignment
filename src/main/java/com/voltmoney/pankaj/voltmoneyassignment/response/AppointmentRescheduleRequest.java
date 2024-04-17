package com.voltmoney.pankaj.voltmoneyassignment.response;

import javax.validation.constraints.NotNull;


// This class is used to get the request for rescheduling the appointment
public class AppointmentRescheduleRequest {
    @NotNull(message = "Operator id is required")
    public int operatorId;

    @NotNull(message = "Scheduled time is required")
    public int scheduledTime;

    @NotNull(message = "Appointment id is required")
    public long appointmentId;
}
