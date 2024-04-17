package com.voltmoney.pankaj.voltmoneyassignment.response;

import javax.validation.constraints.NotNull;

public class AppointmentRescheduleRequest {
    @NotNull(message = "Operator id is required")
    public int operatorId;

    @NotNull(message = "Scheduled time is required")
    public int scheduledTime;

    @NotNull(message = "Appointment id is required")
    public long appointmentId;
}
