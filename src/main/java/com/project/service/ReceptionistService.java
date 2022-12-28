package com.project.service;

import com.project.model.Appointment;
import com.project.model.Contact;
import com.project.model.Patient;
import com.project.model.Receptionist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceptionistService
{
    public Patient addPatient(Patient patient);

    public List<Contact> getPatientContacts(String role);

    public Receptionist checkReceptionist(String username, String password);

    public List<Patient> getPatientList();

    public List<Appointment> getAppointmentList();


}
