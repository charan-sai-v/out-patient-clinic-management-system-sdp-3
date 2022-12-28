package com.project.service;

import com.project.model.Doctor;
import com.project.model.Slot;
import com.project.repository.DoctorRepository;
import com.project.repository.PatientRepository;
import com.project.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService
{
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Override
    public Doctor checkDoctorLogin(String emailPNo, String password) {
        return doctorRepository.checkDoctorLogin(emailPNo, password);
    }

    @Override
    public List<Slot> searchSlotByDoctorID(int id) {
        return slotRepository.searchSlotByDoctorID(id);
    }
}
