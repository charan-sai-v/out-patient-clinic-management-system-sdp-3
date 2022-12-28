package com.project.repository;

import com.project.model.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>
{
    @Query("select p from Patient p where p.email=?1 or p.pNo=?1 and p.password=?2")
    public Patient checkPatientLogin(String emailPNo, String password);

    @Query("update Patient p set p.password=?1 where p.password=?2 and p.id=?3")
    public String changePatientPassword(String newPassword, String oldPassword, int id);



}
