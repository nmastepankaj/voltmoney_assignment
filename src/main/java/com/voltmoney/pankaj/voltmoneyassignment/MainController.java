package com.voltmoney.pankaj.voltmoneyassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;
import com.voltmoney.pankaj.voltmoneyassignment.repository.AppointmentRepository;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentResponse;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentSaveRequest;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentRescheduleRequest;
import com.voltmoney.pankaj.voltmoneyassignment.services.AppointmentService;

@RestController
public class MainController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    public static String getFormattedCurrentDate(Date currentDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(currentDate);
        return strDate;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getALlAppointments() {
        try {
            List<AppointmentResponse> appointments = new ArrayList<AppointmentResponse>();
            
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(currentDate);
            
            List<Appointment> ls = appointmentRepository.findByBookedDate(strDate);
            // ls.forEach(appointments::add);
            // System.out.println("ls: " + ls+ "appointments: " + appointments);
            

            if (ls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            for (Appointment appointment : ls) {
                AppointmentResponse appointmentResponse = new AppointmentResponse();
                appointmentResponse.id = appointment.getId();
                appointmentResponse.operatorId = appointment.getOperatorId();
                appointmentResponse.scheduledTime = String.valueOf(appointment.getScheduledTime())+"-"+String.valueOf(appointment.getScheduledTime()+1);
                appointmentResponse.booked = appointment.isBooked();
                appointments.add(appointmentResponse);
            }

            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_open_appointments")
    public ResponseEntity<List<String>> getOpenAppointments() {
        try {
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(currentDate);
            List<Appointment> ls = appointmentRepository.findByBookedDate(strDate);

            AppointmentService appointmentService = new AppointmentService();
            List<String> openAppointments = appointmentService.getLeftSlotsOfAppoints(ls);
            System.out.println("sanjnka" + openAppointments);

            return new ResponseEntity<>(openAppointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book_appointment")
    public ResponseEntity<String> bookAppointment(@RequestBody AppointmentSaveRequest params) {
        try {
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(currentDate);

            int isExist = appointmentRepository.checkAppointmentPresent(strDate, params.operatorId, params.scheduledTime);

            
            if(isExist>0){
                return new ResponseEntity<>("Appointment Already Booked", HttpStatus.OK);
            }else{
                Appointment appointment = new Appointment();
                appointment.setOperatorId(params.operatorId);
                appointment.setScheduledTime(params.scheduledTime);
                appointment.setBooked(true);
                appointment.setBookedDate(currentDate);
                appointmentRepository.save(appointment);
            }

            return new ResponseEntity<>("Appointment Booked between slot " + String.valueOf(params.scheduledTime)+ "-"+String.valueOf(params.scheduledTime+1), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment Not Booked", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/reschedule_appointment")
    public ResponseEntity<String> rescheduleAppointment(@RequestBody AppointmentRescheduleRequest params) {
        try {
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(currentDate);

            
            
            Appointment appointment_object = appointmentRepository.getAppointmentByAppointmentId(params.appointmentId);
            
            if(appointment_object == null){
                return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
            }else{
                int isExist = appointmentRepository.checkAppointmentPresent(strDate, params.operatorId, params.scheduledTime);
                if(isExist>0){
                    return new ResponseEntity<>("Appointment Already Booked", HttpStatus.OK);
                }
                appointment_object.setScheduledTime(params.scheduledTime);
                appointmentRepository.save(appointment_object);
            }

            return new ResponseEntity<>("Appointment Rescheduled to slot " + String.valueOf(params.scheduledTime)+ "-"+String.valueOf(params.scheduledTime+1), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment Not Rescheduled", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/cancel_appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable("id") long id) {
        try {
            Appointment appointment_object = appointmentRepository.getAppointmentByAppointmentId(id);
            
            if(appointment_object == null){
                return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
            }else{
                appointment_object.setBooked(false);
                appointmentRepository.save(appointment_object);
            }

            return new ResponseEntity<>("Appointment Cancelled", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment Not Cancelled", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}