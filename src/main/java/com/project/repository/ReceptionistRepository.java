package com.project.repository;

import com.project.model.Receptionist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReceptionistRepository extends CrudRepository<Receptionist, Integer>
{

    @Query("select r from Receptionist r where r.email=?1 or r.pNo=?1 and r.password=?2")
    public Receptionist checkReceptionistLogin(String emailPNo, String password);
}
