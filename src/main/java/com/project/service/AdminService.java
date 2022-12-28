package com.project.service;

import com.project.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService
{
    public Admin checkAdminLogin(String username, String password);

    public Doctor addDoctor(Doctor doctor);
    public List<Doctor> viewDoctors();
    public Doctor viewDoctorById(int id);
    public Doctor updateDoctorById(Doctor doctor, int id);
    public void deleteDoctorById(int id);

    public List<Patient> viewAllPatient();
    public Patient viewPatientById(int id);

    public Receptionist addReceptionist(Receptionist receptionist);
    public List<Receptionist> viewReceptionists();
    public Receptionist viewReceptionistById(int id);
    public Receptionist updateReceptionistById(Receptionist receptionist, int id);
    public void deleteReceptionistById(int id);

    public List<Appointment> viewAppointments();


}
