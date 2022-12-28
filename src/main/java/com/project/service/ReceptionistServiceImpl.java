package com.project.service;

import com.project.model.Appointment;
import com.project.model.Contact;
import com.project.model.Patient;
import com.project.model.Receptionist;
import com.project.repository.AppointmentRepository;
import com.project.repository.ContactRepository;
import com.project.repository.PatientRepository;
import com.project.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptionistServiceImpl implements ReceptionistService
{
    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Contact> getPatientContacts(String role) {
        return contactRepository.getPatientContacts(role);
    }

    @Override
    public Receptionist checkReceptionist(String username, String password) {
        return receptionistRepository.checkReceptionistLogin(username, password);
    }

    @Override
    public List<Patient> getPatientList() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentList() {
        return (List<Appointment>) appointmentRepository.findAll();
    }


}
