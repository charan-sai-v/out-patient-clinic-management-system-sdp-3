package com.project.repository;

import com.project.model.Prescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrescriptionRepository extends CrudRepository<Prescription, Integer> {

    @Query("select pr from Prescription pr where pr.patient_id=?1")
    List<Prescription> getPrescriptionByPatientID(int id);

}
