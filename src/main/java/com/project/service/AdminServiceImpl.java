package com.project.service;

import com.project.model.*;
import com.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService
{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Admin checkAdminLogin(String username, String password) {
        return adminRepository.checkAdminLogin(username, password);
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


    @Override
    public List<Doctor> viewDoctors() {
       return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public Doctor viewDoctorById(int id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Doctor updateDoctorById(Doctor doctor, int id) {
        Doctor doctor1 = doctorRepository.findById(id).get();
        doctor1.setSpecialist(doctor.getSpecialist());
        doctor1.setpNo(doctor.getpNo());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setSlot(doctor.getSlot());
        return doctorRepository.save(doctor1);
    }

    @Override
    public void deleteDoctorById(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Patient> viewAllPatient() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public Patient viewPatientById(int id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Receptionist addReceptionist(Receptionist receptionist) {
        return receptionistRepository.save(receptionist);
    }

    @Override
    public List<Receptionist> viewReceptionists() {
        return (List<Receptionist>) receptionistRepository.findAll();
    }

    @Override
    public Receptionist viewReceptionistById(int id) {
        return receptionistRepository.findById(id).get();
    }

    @Override
    public Receptionist updateReceptionistById(Receptionist receptionist, int id) {
        Receptionist receptionist1 = receptionistRepository.findById(id).get();
        receptionist1.setEmail(receptionist.getEmail());
        receptionist1.setpNo(receptionist.getpNo());
        return receptionistRepository.save(receptionist1);
    }

    @Override
    public void deleteReceptionistById(int id) {
        receptionistRepository.deleteById(id);
    }

    @Override
    public List<Appointment> viewAppointments() {
        return (List<Appointment>) appointmentRepository.findAll();
    }
}
