package com.project.repository;

import com.project.model.Doctor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer>
{
    @Query("select d from Doctor d where d.email=?1 or d.pNo=?1 and d.password=?2")
    public Doctor checkDoctorLogin(String emailPNo, String password);


}
