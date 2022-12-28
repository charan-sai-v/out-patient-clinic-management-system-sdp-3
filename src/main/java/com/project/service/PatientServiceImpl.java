package com.project.service;

import com.project.model.*;
import com.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService
{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Patient searchPatientById(int id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Patient checkPatientLogin(String emailPNo, String password) {
        return patientRepository.checkPatientLogin(emailPNo, password);
    }

    @Override
    public Patient updatePatient(Patient patient, int id) {
        Patient patient1 = patientRepository.findById(id).get();
        patient1.setAddress(patient.getAddress());
        patient1.setpNo(patient.getpNo());
        patient1.setEmail(patient.getEmail());
        return patientRepository.save(patient1);
    }

    @Override
    public String changePatientPassword(String oldPassword, String newPassword, int id) {
        return patientRepository.changePatientPassword(newPassword, oldPassword, id);
    }


    @Override
    public List<Appointment> getAppointmentsByPatientID(int id) {
        return appointmentRepository.getAppointmentByPatientID(id);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment viewAppointmentsByDateAndPatientID(String date, int id) {
        return appointmentRepository.getAppointmentByDate(date, id);
    }

    @Override
    public List<Doctor> getDoctorList() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorByID(int id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Slot getSlotByDateAndDoctorID(String date, int id) {
        return slotRepository.searchSlotByDateAndDoctorID(date, id);
    }

    @Override
    public Slot addSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    @Override
    public List<Prescription> getPrescriptionList(int patientID) {
        return prescriptionRepository.getPrescriptionByPatientID(patientID);
    }

    @Override
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
