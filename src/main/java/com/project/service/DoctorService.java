package com.project.service;

import com.project.model.Doctor;
import com.project.model.Slot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService
{
    public Doctor checkDoctorLogin(String emailPNo, String password);

    public List<Slot> searchSlotByDoctorID(int id);



}
