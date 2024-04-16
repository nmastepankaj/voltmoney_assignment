package com.voltmoney.pankaj.voltmoneyassignment.services;

// import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;
import com.voltmoney.pankaj.voltmoneyassignment.repository.AppointmentRepository;

// @Service
public class AppointmentService {
    
    
    public List<String> getLeftSlotsOfAppoints(List<Appointment> ls) {

        
        

        List<String> slots = new ArrayList<String>();
        
        int prev = 0;

        for (Appointment appointment : ls) {
            if (appointment.getScheduledTime() == prev) {
                prev++;
            } else {
                slots.add(prev + "-" + String.valueOf(appointment.getScheduledTime()));
                prev = appointment.getScheduledTime()+1;
            }  
        }
        if(prev != 23){
            slots.add(prev + "-" + 24);
        }
        return slots;
    }
}

