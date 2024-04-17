package com.voltmoney.pankaj.voltmoneyassignment.response;
import javax.validation.constraints.NotNull;


// This class is used to get the request for saving the appointment
public class AppointmentSaveRequest {
    @NotNull(message = "Scheduled time is required")
    public Integer operatorId;

    @NotNull(message = "Scheduled time is required")
    public Integer scheduledTime;
}
