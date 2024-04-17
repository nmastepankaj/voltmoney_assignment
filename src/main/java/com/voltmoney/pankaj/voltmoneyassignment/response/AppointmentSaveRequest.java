package com.voltmoney.pankaj.voltmoneyassignment.response;
import javax.validation.constraints.NotNull;



public class AppointmentSaveRequest {
    @NotNull(message = "Scheduled time is required")
    public Integer operatorId;

    @NotNull(message = "Scheduled time is required")
    public Integer scheduledTime;
}
