package com.voltmoney.pankaj.voltmoneyassignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltmoney.pankaj.voltmoneyassignment.models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT * FROM appointments a WHERE DATE(a.booked_date) = :date and operator_id = :operatorId and booked = true order by scheduled_time", nativeQuery = true)
    List<Appointment> findByBookedDate(@Param("date") String date, @Param("operatorId") int operatorId);

    @Query(value = "SELECT count(*) FROM appointments a WHERE DATE(a.booked_date) = :date and scheduled_time = :st and operator_id = :operatorId and booked = true", nativeQuery = true)
    int checkAppointmentPresent(@Param("date") String date,@Param("operatorId") int operatorId, @Param("st") int st);

    @Query(value = "SELECT * FROM appointments a WHERE id = :appointmentId and booked = true limit 1", nativeQuery = true)
    Appointment getAppointmentByAppointmentId(@Param("appointmentId") long appointmentId);

}