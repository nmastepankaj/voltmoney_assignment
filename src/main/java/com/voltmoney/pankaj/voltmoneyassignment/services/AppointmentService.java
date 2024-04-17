package com.voltmoney.pankaj.voltmoneyassignment.services;

import java.util.ArrayList;
import java.util.List;

import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;

public class AppointmentService {

    public List<String> getLeftSlotsOfAppoints(List<Appointment> ls) {

        List<String> slots = new ArrayList<String>();

        int prev = 0;

        for (Appointment appointment : ls) {
            if (appointment.getScheduledTime() == prev) {
                prev++;
            } else {
                slots.add(prev + "-" + String.valueOf(appointment.getScheduledTime()));
                prev = appointment.getScheduledTime() + 1;
            }
        }
        if (prev != 24) {
            slots.add(prev + "-" + 24);
        }
        return slots;
    }
}
