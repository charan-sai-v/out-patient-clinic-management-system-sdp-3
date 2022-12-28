package com.project.service;

import com.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PatientService
{
    public Patient searchPatientById(int id);
    public Patient checkPatientLogin(String emailPNo, String password);

    public Patient updatePatient(Patient patient, int id);

    public String changePatientPassword(String oldPassword, String newPassword, int id);

    public List<Appointment> getAppointmentsByPatientID(int id);

    public Appointment addAppointment(Appointment appointment);

    public Appointment viewAppointmentsByDateAndPatientID(String date, int id);

    public List<Doctor> getDoctorList();

    public Doctor getDoctorByID(int id);

    public Slot getSlotByDateAndDoctorID(String date, int id);

    public Slot addSlot(Slot slot);

    public List<Prescription> getPrescriptionList(int patientID);

    public Contact addContact(Contact contact);
}
