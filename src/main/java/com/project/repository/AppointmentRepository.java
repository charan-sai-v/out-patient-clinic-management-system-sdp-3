package com.project.repository;

import com.project.model.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    @Query("select a from Appointment a where a.patient_id=?1")
    public List<Appointment> getAppointmentByPatientID(int id);

    @Query("select a from Appointment a where a.date=?1 and a.patient_id=?2")
    public Appointment getAppointmentByDate(String date, int id);
}
