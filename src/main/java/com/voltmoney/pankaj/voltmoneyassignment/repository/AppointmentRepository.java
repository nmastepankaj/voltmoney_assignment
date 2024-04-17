package com.voltmoney.pankaj.voltmoneyassignment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;


// This interface is used to interact with the database for the appointments
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // This method is used to get all the appointments for the operator for the current date
    // Use native query due to Date function is not supported by spring Data JPA
    @Query(value = "SELECT * FROM appointments a WHERE DATE(a.booked_date) = :date and operator_id = :operatorId and booked = true order by scheduled_time", nativeQuery = true)
    List<Appointment> findByBookedDate(@Param("date") String date, @Param("operatorId") int operatorId);

    // This method is used to count the appointment for the operator for the current date and scheduled time fpr checking if the appointment is present or not
    // Use native query due to Date function is not supported by spring Data JPA
    @Query(value = "SELECT count(*) FROM appointments a WHERE DATE(a.booked_date) = :date and scheduled_time = :st and operator_id = :operatorId and booked = true", nativeQuery = true)
    int checkAppointmentPresent(@Param("date") String date,@Param("operatorId") int operatorId, @Param("st") int st);

    // This method is used to get the appointment by the appointment id
    @Query(value = "SELECT * FROM appointments a WHERE id = :appointmentId and booked = true limit 1", nativeQuery = true)
    Appointment getAppointmentByAppointmentId(@Param("appointmentId") long appointmentId);

    List<Appointment> findByBookedDateAndOperatorIdAndBooked(Date date, int operatorId, boolean booked);

}