package com.voltmoney.pankaj.voltmoneyassignment.services;

import java.util.ArrayList;
import java.util.List;

import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;

public class AppointmentService {
    /* Appointment related service class */

    public List<String> getLeftSlotsOfAppoints(List<Appointment> ls) {
        // This method is used to get the left slots of the appointment
        // It will return the list of slots which are not booked

        List<String> slots = new ArrayList<String>();

        int prev = 0;   // previous booked slot (for finding the left slots in range)

        for (Appointment appointment : ls) {
            if (appointment.getScheduledTime() == prev) {
                // if the current slot is same as the previous slot then it means the previous slot is also booked
                prev++;
            } else {
                slots.add(prev + "-" + String.valueOf(appointment.getScheduledTime()));
                prev = appointment.getScheduledTime() + 1;
            }
        }
        if (prev != 24) {
            // if the prev slot is not 24 then add the last left slot is prev-24
            slots.add(prev + "-" + 24);
        }
        return slots;
    }
}
