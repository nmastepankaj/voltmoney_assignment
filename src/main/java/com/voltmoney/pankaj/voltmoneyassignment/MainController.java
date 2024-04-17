package com.voltmoney.pankaj.voltmoneyassignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;
import com.voltmoney.pankaj.voltmoneyassignment.repository.AppointmentRepository;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentResponse;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentSaveRequest;
import com.voltmoney.pankaj.voltmoneyassignment.response.AppointmentRescheduleRequest;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    public static String getFormattedCurrentDate() {
        // This method is used to get the formatted current date
        // It will return the current date in the format yyyy-MM-dd

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(currentDate);
        return strDate;
    }

    @GetMapping("/appointments/{operatorId}")
    public ResponseEntity<List<AppointmentResponse>> getALlAppointments(@PathVariable("operatorId") int operatorId) {
        // This method is used to get all the appointments for the operator for the
        // current date
        try {
            List<AppointmentResponse> appointments = new ArrayList<AppointmentResponse>();

            String strDate = getFormattedCurrentDate();

            // getting all the appointments for the operator for the current date
            List<Appointment> ls = appointmentRepository.findByBookedDate(strDate, operatorId);

            if (ls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            for (Appointment appointment : ls) {
                AppointmentResponse appointmentResponse = new AppointmentResponse();
                appointmentResponse.id = appointment.getId();
                appointmentResponse.operatorId = appointment.getOperatorId();
                appointmentResponse.scheduledTime = String.valueOf(appointment.getScheduledTime()) + "-"
                        + String.valueOf(appointment.getScheduledTime() + 1);
                appointmentResponse.booked = appointment.isBooked();
                appointments.add(appointmentResponse);
            }

            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_open_appointments/{operatorId}")
    public ResponseEntity<List<String>> getOpenAppointments(@PathVariable("operatorId") int operatorId) {
        // This method is used to get the open appointments for the operator for the
        // current date
        try {
            String strDate = getFormattedCurrentDate();
            List<Appointment> ls = appointmentRepository.findByBookedDate(strDate, operatorId);

            
            // getting the left slots of the appointments
            List<String> slots = new ArrayList<String>();

            int prev = 0; // previous booked slot (for finding the left slots in range)

            for (Appointment appointment : ls) {
                if (appointment.getScheduledTime() == prev) {
                    // if the current slot is same as the previous slot then it means the previous
                    // slot is also booked
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

            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book_appointment")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentSaveRequest params) {
        // This method is used to book the appointment for the operator for the current date

        try {
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = formatter.format(currentDate);

            if(params.scheduledTime < 0 || params.scheduledTime > 23){
                return new ResponseEntity<>("Invalid Time Slot", HttpStatus.BAD_REQUEST);
            }

            // checking if the appointment is already booked or not
            int isExist = appointmentRepository.checkAppointmentPresent(strDate, params.operatorId,
                    params.scheduledTime);

            if (isExist > 0) {
                return new ResponseEntity<>("Appointment Already Booked", HttpStatus.BAD_REQUEST);
            } else {
                Appointment appointment = new Appointment();
                appointment.setOperatorId(params.operatorId);
                appointment.setScheduledTime(params.scheduledTime);
                appointment.setBooked(true);
                appointment.setBookedDate(currentDate);
                appointmentRepository.save(appointment);
            }

            return new ResponseEntity<>("Appointment Booked between slot " + String.valueOf(params.scheduledTime) + "-"
                    + String.valueOf(params.scheduledTime + 1), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Appointment Not Booked", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/reschedule_appointment")
    public ResponseEntity<String> rescheduleAppointment(@RequestBody AppointmentRescheduleRequest params) {
        // This method is used to reschedule the appointment for the operator for the current date

        try {
            if(params.scheduledTime < 0 || params.scheduledTime > 23){
                return new ResponseEntity<>("Invalid Time Slot", HttpStatus.BAD_REQUEST);
            }
            
            String strDate = getFormattedCurrentDate();

            // getting appointment object by the appointment id
            Appointment appointment_object = appointmentRepository.getAppointmentByAppointmentId(params.appointmentId);

            if (appointment_object == null) {
                return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
            } else {
                // checking if the appointment is already booked or not
                int isExist = appointmentRepository.checkAppointmentPresent(strDate, params.operatorId,
                        params.scheduledTime);
                if (isExist > 0) {
                    return new ResponseEntity<>("Appointment Already Booked", HttpStatus.BAD_REQUEST);
                }
                appointment_object.setScheduledTime(params.scheduledTime);
                appointmentRepository.save(appointment_object);
            }

            return new ResponseEntity<>("Appointment Rescheduled to slot " + String.valueOf(params.scheduledTime) + "-"
                    + String.valueOf(params.scheduledTime + 1), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment Not Rescheduled", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/cancel_appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable("id") long id) {
        // This method is used to cancel the appointment for the operator for the
        // current date

        try {
            Appointment appointment_object = appointmentRepository.getAppointmentByAppointmentId(id);

            if (appointment_object == null) {
                return new ResponseEntity<>("Appointment doesn't exist", HttpStatus.NOT_FOUND);
            } else {
                appointment_object.setBooked(false);
                appointmentRepository.save(appointment_object);
            }

            return new ResponseEntity<>("Appointment Cancelled", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment Not Cancelled", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
