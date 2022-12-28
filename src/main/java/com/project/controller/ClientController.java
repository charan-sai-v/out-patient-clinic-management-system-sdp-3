package com.project.controller;

import com.project.model.*;
import com.project.service.AdminService;
import com.project.service.DoctorService;
import com.project.service.PatientService;
import com.project.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ClientController {

    @Autowired
    private AdminService adminService;

    // --- Index
    @GetMapping("/")
    public String Home() {
        return "index";
    }

    // --- Login Portal
    @GetMapping("login")
    public String Login(){
        return "loginportal";
    }


    // --- Admin Controller ------------------------------------------------
    // -- Admin Login Get
    @GetMapping("adminlogin")
    public String AdminLogin(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "role");
        if  (cookie == null){
            return "adminlogin";
        }
        return "redirect:/admindashboard";
    }

    // -- Admin LoginCheck Post
    @PostMapping(value = "checkadmin")
    @ResponseBody
    public ModelAndView CheckAdminLogin(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView mv = new ModelAndView();
        Admin checkAdminLogin = adminService.checkAdminLogin(username, password);
        if(checkAdminLogin != null) {
            Cookie cookie = new Cookie("role","admin");
            cookie.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie);
            mv.setViewName("redirect:/admindashboard");
        }
        else{
            mv.setViewName("adminlogin");
            mv.addObject("msg", "Login Failed");
        }
        return mv;
    }

    // -- Admin SignOut
    @GetMapping("adminlogout")
    public String adminLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("role",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/adminlogin";

    }

    // -- Admin Dashboard Get
    @GetMapping("admindashboard")
    public ModelAndView adminDashboard(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if(cookie != null){
            mv.setViewName("admindashboard");
        }
        else{
            mv.setViewName("redirect:/adminlogin");
        }
        return mv;
    }

    // -- Admin Add Doctor
    @GetMapping("adminadddoctor")
    public ModelAndView adminAddDoctor(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if (cookie != null){
            mv.setViewName("adminadddoctor");
        } else {
            mv.setViewName("redirect:/adminlogin");
        }
        return mv;
    }

    // -- Admin Add Doctor Post
    @PostMapping("adminadddoctor")
    public ModelAndView adminAddDoctorPost(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if (cookie != null){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            String specialist = request.getParameter("specialist");
            String slot = request.getParameter("slot");
            String pNo = request.getParameter("pNo");
            Doctor doctor = new Doctor();
            doctor.setName(name);
            doctor.setEmail(email);
            doctor.setPassword(password);
            doctor.setGender(gender);
            doctor.setSpecialist(specialist);
            doctor.setSlot(slot);
            doctor.setpNo(pNo);
            doctor.setNo_of_patients_treated(0);
            doctor.setRating(0);
            Doctor doctor1 = adminService.addDoctor(doctor);
            if (doctor1 != null){
                mv.addObject("msg", "created");
            } else {
                mv.addObject("msg", "not");
            }
            mv.setViewName("redirect:/admindoctors");
        } else {
            mv.setViewName("redirect:/adminlogin");
        }
        return mv;
    }

    // -- Admin Doctors List
    @GetMapping("admindoctors")
    public ModelAndView adminDoctorsList(){
        ModelAndView mv = new ModelAndView("admindoctorlist");

        List<Doctor> doctorList = adminService.viewDoctors();
        return mv.addObject("doctorList", doctorList);
    }

    // -- Admin Doctor Edit
    @GetMapping("admindoctoredit/{id}")
    public ModelAndView adminDoctorEdit(@PathVariable int id){
        ModelAndView mv = new ModelAndView("admindoctoredit");
        Doctor doctor = adminService.viewDoctorById(id);
        mv.addObject("doctor", doctor);
        return mv;
    }

    // -- Admin Doctor Update
    @PostMapping("admindoctorupdate/{id}")
    public ModelAndView adminDoctorUpdate(@PathVariable int id, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String s = "success";
        try {
            String specialist = request.getParameter("specialist");
            String pNo = request.getParameter("pNo");
            String email = request.getParameter("email");
            String slot = request.getParameter("slot");
            Doctor doctor = adminService.viewDoctorById(id);
            doctor.setSpecialist(specialist);
            doctor.setpNo(pNo);
            doctor.setEmail(email);
            doctor.setSlot(slot);
            Doctor doctor1 = adminService.updateDoctorById(doctor, id);

            mv.addObject("msg", s);
        }catch (Exception e){
            s = "failed";
            mv.addObject("msg", s);
        }
        mv.setViewName("redirect:/admindoctors");
        return mv;
    }

    // -- Admin Doctor Delete
    @DeleteMapping("admindoctordelete/{id}")
    public ModelAndView adminDoctorDelete(@PathVariable int id){
        adminService.deleteDoctorById(id);
        ModelAndView mv = new ModelAndView("redirect:/admindoctors");
        mv.addObject("msg", "removed");
        return mv;
    }

    // -- Admin Receptionist Add
    @GetMapping("adminaddreceptionist")
    public ModelAndView adminAddReceptionist(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if (cookie != null){
            modelAndView.setViewName("adminaddreceptionist");
        } else {
            modelAndView.setViewName("redirect:/adminlogin");
        }
        return modelAndView;
    }

    // -- Admin Receptionist Add POST
    @PostMapping("adminaddreceptionist")
    public ModelAndView adminAddReceptionistPost(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if (cookie != null){
            try {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String pNo = request.getParameter("pNo");
                String gender = request.getParameter("gender");
                Receptionist receptionist = new Receptionist();
                receptionist.setName(name);
                receptionist.setEmail(email);
                receptionist.setpNo(pNo);
                receptionist.setPassword(password);
                receptionist.setGender(gender);
                Receptionist receptionist1 = adminService.addReceptionist(receptionist);
                if (receptionist1 != null){
                    mv.addObject("msg", "created");
                    mv.setViewName("redirect:/adminreceptionistlist");
                } else {
                    mv.addObject("msg", "failed");
                    mv.setViewName("adminaddreceptionist");
                }
            } catch (Exception e){
                System.out.println(e);
            }
        } else {
            mv.setViewName("redirect:/adminlogin");
        }
        return mv;
    }

    // -- Admin Receptionist List
    @GetMapping("adminreceptionistlist")
    public ModelAndView adminReceptionistList(){
        ModelAndView mv = new ModelAndView("adminreceptionistlist");
        List<Receptionist> receptionistList = adminService.viewReceptionists();
        return mv.addObject("receptionistList", receptionistList);
    }

    // -- Admin Receptionist Edit
//    @GetMapping("adminreceptionistedit/{id}")
//    public ModelAndView adminReceptionistEdit(@PathVariable int id){
//        ModelAndView mv = new ModelAndView("receptionistedit");
//        Receptionist receptionist = adminService.viewReceptionistById(id);
//        mv.addObject("receptionist", receptionist);
//        return mv;
//    }

    // -- Admin Receptionist Update
//    @PostMapping("adminreceptionistupdate/{id}")
//    public ModelAndView adminReceptionistUpdate(@PathVariable int id, @RequestBody Receptionist receptionist){
//        ModelAndView mv = new ModelAndView();
//        try {
//            Receptionist receptionist1 = adminService.updateReceptionistById(receptionist, id);
//            mv.addObject("msg", "Updated Successfully");
//        }catch (Exception e){
//            mv.addObject("msg", "Updated Failed");
//        }
//        return mv;
//    }

    // -- Admin Receptionist Delete
    @GetMapping("adminreceptionistdelete/{id}")
    public ModelAndView adminReceptionistDelete(@PathVariable int id){
        ModelAndView mv = new ModelAndView();
        adminService.deleteReceptionistById(id);
        mv.addObject("msg","deleted");
        mv.setViewName("redirect:/adminreceptionistlist");
        return mv;
    }

    // -- Admin Patient List
    @GetMapping("adminpatientlist")
    public ModelAndView adminPatientList(){
        ModelAndView mv = new ModelAndView("adminpatientlist");
        List<Patient> patientList = adminService.viewAllPatient();
        mv.addObject("patientList", patientList);
        return mv;
    }

    // -- Admin Appointments List
    @GetMapping("adminappointmentlist")
    public ModelAndView adminAppointmentlist(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if (cookie != null){
            List<Appointment> appointmentList = adminService.viewAppointments();
            mv.addObject("appointmentList", appointmentList);
            mv.setViewName("adminappointments");
        } else {
            mv.setViewName("redirect:/adminlogin");
        }
        return mv;
    }



    // --- Doctor Controller -------------------------------------------------------------

    @Autowired
    private DoctorService doctorService;

    // -- Doctor Login
    @GetMapping("doctorlogin")
    public String doctorLogin(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, "role");
        if  (cookie == null){
            return "doctorlogin";
        }
        return "redirect:/doctordashboard";
    }

    // --- Doctor Post
    @PostMapping("doctorcheck")
    public ModelAndView doctorCheck(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        ModelAndView mv = new ModelAndView();
        Doctor checkDoctorLogin = doctorService.checkDoctorLogin(username, password);
        if(checkDoctorLogin != null) {
            Cookie cookie = new Cookie("role","doctor");
            cookie.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie);
            Cookie cookie1 = new Cookie("id",Integer.toString(checkDoctorLogin.getId()));
            cookie1.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie1);
            mv.setViewName("redirect:/doctordashboard");
        }
        else{
            mv.setViewName("doctorlogin");
            mv.addObject("msg", "Login Failed");
        }
        return mv;
    }


    // -- Doctor SignOut
    @GetMapping("doctorlogout")
    public String doctorLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("role",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Cookie cookie1 = new Cookie("id",null);
        cookie1.setMaxAge(0);
        response.addCookie(cookie1);
        return "redirect:/doctorlogin";
    }

    // -- Doctor Dashboard
    @GetMapping("doctordashboard")
    public ModelAndView doctorDashboard(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "role");
        if(cookie != null){
            mv.setViewName("doctordashboard");
        }
        else{
            mv.setViewName("redirect:/doctorlogin");
        }
        return mv;
    }

    // -- Doctor Schedules
    @GetMapping("doctorslots")
    public ModelAndView doctorSlots(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            List<Slot> slotList = doctorService.searchSlotByDoctorID(Integer.parseInt(cookie.getValue()));
            mv.addObject("slotList", slotList);
            mv.setViewName("doctordashboard");
        }
        else{
            mv.setViewName("redirect:/doctorlogin");
        }
        return mv;
    }

    // --- Patient Controller --------------------------------------

    @Autowired
    private PatientService patientService;

    // -- Patient Login
    @GetMapping("patientlogin")
    public ModelAndView patientLogin(){
        return new ModelAndView("patientlogin");
    }

    // -- Patient Check
    @PostMapping("patientcheck")
    public ModelAndView patientCheck(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        ModelAndView mv = new ModelAndView();
        Patient checkPatientLogin = patientService.checkPatientLogin(username, password);
        int id = checkPatientLogin.getId();
        if(checkPatientLogin != null) {
            Cookie cookie = new Cookie("id", Integer.toString(id));
            cookie.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie);
            mv.setViewName("redirect:/patientdashboard");
        }
        else{
            mv.setViewName("patientlogin");
            mv.addObject("msg", "Login Failed");
        }
        return mv;
    }

    // -- Patient Dashboard
    @GetMapping("patientdashboard")
    public ModelAndView patientDashboard(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            mv.setViewName("patientdashboard");
        }
        else{
            mv.setViewName("redirect:/patientlogin");
        }
        return mv;
    }

    // -- Patient Logout
    @GetMapping("patientlogout")
    public String patientLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("id",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/patientlogin";
    }


    // -- Patient Appointment
    @GetMapping("patientappointments")
    public ModelAndView patientAppointments(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("d/MM/yyyy");
            String date_1 = format1.format(tomorrow);
            Appointment appointment = patientService.viewAppointmentsByDateAndPatientID(date_1, Integer.parseInt(cookie.getValue()));
            modelAndView.addObject("appointment", appointment);
            List<Appointment> appointmentList = patientService.getAppointmentsByPatientID(Integer.parseInt(cookie.getValue()));
            modelAndView.addObject("appointmentlist", appointmentList);
            modelAndView.setViewName("patientappointment");
        }
        else{
        modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Doctors
    @GetMapping("patientdoctors")
    public ModelAndView patientDoctors(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            List<Doctor> doctorList = patientService.getDoctorList();
            modelAndView.addObject("doctorList", doctorList);
            modelAndView.setViewName("patientdoctors");
        }
        else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Add Appointment
    @GetMapping("patientaddappointment/{id}")
    public ModelAndView patientAddAppointment(@PathVariable int id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            Doctor doctor =  patientService.getDoctorByID(id);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            // now get "tomorrow"
            Date tomorrow = calendar.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("d/MM/yyyy");
            String sDate_1 = format1.format(tomorrow);
            // print out tomorrow's date

                Optional<Slot> slot1 = Optional.ofNullable(patientService.getSlotByDateAndDoctorID(sDate_1, id));
                if (slot1.isPresent()){
                    System.out.println(slot1.get().getDate());
                    modelAndView.addObject("slot1", slot1);
                }
                else{
                    Slot slot = new Slot();
                    slot.setDate(sDate_1);
                    slot.setDoctor_id(id);
                    Slot slot_1 = patientService.addSlot(slot);
                    Optional<Slot> slotList1 = Optional.ofNullable(slot_1);
                    modelAndView.addObject("slot1", slotList1);
                }

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date date_2 = calendar.getTime();
            String sDate_2 = format1.format(date_2);

            Optional<Slot> slot2 = Optional.ofNullable(patientService.getSlotByDateAndDoctorID(sDate_2, id));
            if (slot2.isPresent()){
                System.out.println(slot2.get().getDate());
                modelAndView.addObject("slot2", slot2);
            }
            else{
                Slot slot = new Slot();
                slot.setDate(sDate_2);
                slot.setDoctor_id(id);
                Slot slot_2 = patientService.addSlot(slot);
                Optional<Slot> slotList2 = Optional.ofNullable(slot_2);
                modelAndView.addObject("slot2", slotList2);
            }

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date date_3 = calendar.getTime();
            String sDate_3 = format1.format(date_3);

            Optional<Slot> slot3 = Optional.ofNullable(patientService.getSlotByDateAndDoctorID(sDate_3, id));
            if (slot3.isPresent()){
                System.out.println(slot3.get().getDate());
                modelAndView.addObject("slot3", slot3);
            }
            else{
                Slot slot = new Slot();
                slot.setDate(sDate_3);
                slot.setDoctor_id(id);
                Slot slot_3 = patientService.addSlot(slot);
                Optional<Slot> slotList3 = Optional.ofNullable(slot_3);
                modelAndView.addObject("slot3", slotList3);
            }

            modelAndView.addObject("doctor", doctor);
            modelAndView.setViewName("patientaddappointment");
        }
        else{
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Add Appointment Post
    @PostMapping("patientaddappointment")
    public ModelAndView patientAddAppointmentPost(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null) {
            String shift = request.getParameter("shift");
            String slot = request.getParameter("slot");
            String doctorID = request.getParameter("doctor");
            String date = "";
            String date_1 = request.getParameter("date-1");
            String date_2 = request.getParameter("date-2");
            String date_3 = request.getParameter("date-3");
            String doctorName = request.getParameter("doctor-name");

            if(!date_1.isEmpty()){
                date = date_1;
            } else if (!date_2.isEmpty()) {
                date = date_2;
            } else {
                date = date_3;
            }

            Slot slot1 = patientService.getSlotByDateAndDoctorID(date, Integer.parseInt(doctorID));
            int patientID = Integer.parseInt(cookie.getValue());
            // appointment
            Appointment appointment = new Appointment();
            appointment.setPatient_id(patientID);
            appointment.setDoctor_id(Integer.parseInt(doctorID));
            appointment.setDate(date);
            appointment.setDoctor_name(doctorName);

            if (slot.equals("slot1")) {
                slot1.setSlot_1(1);
                if (shift == "morning") {
                    appointment.setStart_time("9:00 AM");
                    appointment.setEnd_time("9:15 AM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("1:00 PM");
                    appointment.setEnd_time("1:15 PM");
                } else {
                    appointment.setStart_time("5:00 PM");
                    appointment.setEnd_time("5:15 PM");
                }

            } else if (slot.equals("slot2")) {
                slot1.setSlot_2(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("9:15 AM");
                    appointment.setEnd_time("9:30 AM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("1:15 PM");
                    appointment.setEnd_time("1:30 PM");

                } else {
                    appointment.setStart_time("5:15 PM ");
                    appointment.setEnd_time("5:30 PM");

                }
            } else if (slot.equals("slot3")) {
                slot1.setSlot_3(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("9:30 AM");
                    appointment.setEnd_time("9:45 AM");
                } else if (shift == "afternoon") {
                    appointment.setStart_time("1:30 PM");
                    appointment.setEnd_time("1:45 PM");
                } else {
                    appointment.setStart_time("5:30 PM ");
                    appointment.setEnd_time("5:45 PM");
                }
            } else if (slot.equals("slot4")) {
                slot1.setSlot_4(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("9:45 AM");
                    appointment.setEnd_time("10:00 AM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("1:45 PM");
                    appointment.setEnd_time("2:00 PM");

                } else {
                    appointment.setStart_time("5:45 PM ");
                    appointment.setEnd_time("6:00 PM");

                }
            } else if (slot.equals("slot5")) {
                slot1.setSlot_5(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("10:00 AM");
                    appointment.setEnd_time("10:15 AM");
                } else if (shift == "afternoon") {
                    appointment.setStart_time("2:00 PM");
                    appointment.setEnd_time("2:15 PM");

                } else {
                    appointment.setStart_time("6:00 PM ");
                    appointment.setEnd_time("6:15 PM");
                }
            } else if (slot.equals("slot6")) {
                slot1.setSlot_6(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("10:15 AM");
                    appointment.setEnd_time("10:30 AM");
                } else if (shift == "afternoon") {
                    appointment.setStart_time("2:15 PM");
                    appointment.setEnd_time("2:30 PM");

                } else {
                    appointment.setStart_time("6:15 PM ");
                    appointment.setEnd_time("6:30 PM");

                }
            } else if (slot.equals("slot7")) {
                slot1.setSlot_7(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("11:00 AM");
                    appointment.setEnd_time("11:15 AM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("3:00 PM");
                    appointment.setEnd_time("3:15 PM");

                } else {
                    appointment.setStart_time("7:00 PM ");
                    appointment.setEnd_time("7:15 PM");

                }
            } else if (slot.equals("slot8")) {
                slot1.setSlot_8(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("11:15 PM");
                    appointment.setEnd_time("11:30 PM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("3:15 PM");
                    appointment.setEnd_time("3:30 PM");

                } else {
                    appointment.setStart_time("7:15 PM ");
                    appointment.setEnd_time("7:30 PM");

                }
            } else if (slot.equals("slot9")) {
                slot1.setSlot_9(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("11:30 AM");
                    appointment.setEnd_time("11:45 AM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("3:30 PM");
                    appointment.setEnd_time("3:45 PM");

                } else {
                    appointment.setStart_time("7:30 PM ");
                    appointment.setEnd_time("7:45 PM");

                }
            } else if (slot.equals("slot10")) {
                slot1.setSlot_10(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("11:45 AM");
                    appointment.setEnd_time("12:00 PM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("3:45 PM");
                    appointment.setEnd_time("4:00 PM");

                } else {
                    appointment.setStart_time("7:45 PM");
                    appointment.setEnd_time("8:00 PM");

                }
            } else if (slot.equals("slot11")) {
                slot1.setSlot_11(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("12:00 PM");
                    appointment.setEnd_time("12:15 PM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("4:00 PM");
                    appointment.setEnd_time("4:15 PM");

                } else {
                    appointment.setStart_time("8:00 PM ");
                    appointment.setEnd_time("8:15 PM");

                }
            } else if (slot.equals("slot12")){
                slot1.setSlot_12(patientID);
                if (shift == "morning") {
                    appointment.setStart_time("12:15 PM");
                    appointment.setEnd_time("12:30 PM");

                } else if (shift == "afternoon") {
                    appointment.setStart_time("4:15 PM");
                    appointment.setEnd_time("4:30 PM");

                } else {
                    appointment.setStart_time("8:15 PM");
                    appointment.setEnd_time("8:30 PM");
                }
            }

            patientService.addSlot(slot1);
            patientService.addAppointment(appointment);
            mv.setViewName("redirect:/patientappointments");
        }
        return mv;
    }

    // -- Patient Profile
    @GetMapping("patientprofile")
    public ModelAndView patientProfile(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if(cookie != null){
            Patient patient = patientService.searchPatientById(Integer.parseInt(cookie.getValue()));
            modelAndView.addObject("patient", patient);
            modelAndView.setViewName("patientprofile");
        }
        else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Profile Update
    @GetMapping("patientprofileupdate")
    public ModelAndView patientProfileUpdate(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            Patient patient = patientService.searchPatientById(Integer.parseInt(cookie.getValue()));
            mv.addObject("patient", patient);
            mv.setViewName("patientprofileupdate");
        } else{
            mv.setViewName("redirect:/patientlogin");
        }
        return mv;
    }

    // -- Patient Profile Update Post
    @PostMapping("patientprofileupdate")
    public ModelAndView patientProfileUpdatePost(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            int id = Integer.parseInt(cookie.getValue());
            String firstName = request.getParameter("fName");
            String lastName = request.getParameter("lName");
            String email = request.getParameter("email");
            String pNo = request.getParameter("pNo");
            String address = request.getParameter("address");
            Patient patient = patientService.searchPatientById(id);
            patient.setEmail(email);
            patient.setpNo(pNo);
            patient.setAddress(address);
            patient.setfName(firstName);
            patient.setlName(lastName);
            Patient patient1  = patientService.updatePatient(patient, id);
            if (patient1 != null){
                modelAndView.addObject("updated","Yes");
            } else {
                modelAndView.addObject("updated", "No");
            }
            modelAndView.setViewName("patientprofile");
        }
        else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Password Update
    @GetMapping("patientupdatepassword")
    public ModelAndView patientUpdatePassword(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            int id = Integer.parseInt(cookie.getValue());
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String passwordUpdateCheck = patientService.changePatientPassword(oldPassword, newPassword, id);
            if(!passwordUpdateCheck.isEmpty()){
                modelAndView.addObject("updated", "Yes");
            } else{
                modelAndView.addObject("updated", "No");
            }
            modelAndView.setViewName("redirect:/patientprofile");
        }
        else{
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Prescription
    @GetMapping("patientprescriptions")
    public ModelAndView patientPrescription(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            int id = Integer.parseInt(cookie.getValue());
            List<Prescription> prescriptionList = patientService.getPrescriptionList(id);
            modelAndView.addObject("prescriptionList", prescriptionList);
            modelAndView.setViewName("patientprescription");
        } else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Contact
    @GetMapping("patientcontact")
    public ModelAndView patientContact(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            modelAndView.setViewName("patientcontact");
        } else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // -- Patient Contact Post
    @PostMapping("patientcontact")
    public ModelAndView patientContactPost(HttpServletRequest request, @RequestBody Contact contact){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            int id = Integer.parseInt(cookie.getValue());
            contact.setRole("patient");
            contact.setRole_id(id);
            Contact contact1 = patientService.addContact(contact);
            if (contact != null) {
                modelAndView.addObject("sent", "Yes");
            } else {
                modelAndView.addObject("sent", "No");
            }
            modelAndView.setViewName("patientcontact");
        } else {
            modelAndView.setViewName("redirect:/patientlogin");
        }
        return modelAndView;
    }

    // --- Receptionist Controllers ------------------------------------------------------

    @Autowired
    private ReceptionistService receptionistService;

    // -- Receptionist Login
    @GetMapping("receptionistlogin")
    public ModelAndView receptionistLogin(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("receptionistlogin");
//        Cookie cookie = WebUtils.getCookie(request, "id");
//        if (cookie == null){
//            modelAndView.setViewName("redirect:/receptionistdashboard");
//        } else {
//            modelAndView.setViewName("receptionistlogin");
//        }
        return modelAndView;
    }

    // -- Receptionist Checklogin
    @PostMapping("receptionistcheck")
    public ModelAndView receptionistCheck(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        Receptionist receptionist = receptionistService.checkReceptionist(username, password);
        if (receptionist != null) {
            Cookie cookie = new Cookie("role","receptionist");
            cookie.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie);
            Cookie cookie1 = new Cookie("id",Integer.toString(receptionist.getId()));
            cookie1.setMaxAge(24 * 60 * 60); // expires in 1 day
            response.addCookie(cookie1);
            modelAndView.setViewName("redirect:/receptionistdashboard");
        } else {
            modelAndView.addObject("msg", "Login Failed");
            modelAndView.setViewName("receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Logout
    @GetMapping("recetpionistlogout")
    public String receptionistLogout(HttpServletResponse response){
        Cookie cookie = new Cookie("id",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/receptionistlogin";
    }

    // -- Receptionist Dashboard
    @GetMapping("receptionistdashboard")
    public ModelAndView receptionistDashboard(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            modelAndView.setViewName("receptionistdashboard");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Patients
    @GetMapping("receptionistpatients")
    public ModelAndView receptionist(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            List<Patient> patientList = receptionistService.getPatientList();
            modelAndView.addObject("patientList", patientList);
            modelAndView.setViewName("receptionistpatients");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Add Patient
    @GetMapping("receptionistaddpatient")
    public ModelAndView receptionistAddPatient(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null) {
           modelAndView.setViewName("receptionistaddpatient");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Add Patient Post
    @PostMapping("receptionistaddpatient")
    public ModelAndView receptionistAddPatientPost(HttpServletRequest request, @RequestBody Patient patient){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null) {
            Patient patient1 = receptionistService.addPatient(patient);
            if (patient1 != null) {
                modelAndView.addObject("msg","success");
            } else {
                modelAndView.addObject("msg", "failed");
            }
            modelAndView.setViewName("receptionistpatients");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Patient Appointments
    @GetMapping("receptionistappointments")
    public ModelAndView receptionistViewAppointments(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null) {
            List<Appointment> appointmentList = receptionistService.getAppointmentList();
            modelAndView.addObject("appointmentList", appointmentList);
            modelAndView.setViewName("receptionistappointments");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

    // -- Receptionist Patient Contacts
    @GetMapping("receptionistpatientcontacts")
    public ModelAndView receptionistViewPatientContacts(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        Cookie cookie = WebUtils.getCookie(request, "id");
        if (cookie != null){
            List<Contact> contactList = receptionistService.getPatientContacts("patient");
            modelAndView.addObject("contactList", contactList);
            modelAndView.setViewName("receptionistpatientcontacts");
        } else {
            modelAndView.setViewName("redirect:/receptionistlogin");
        }
        return modelAndView;
    }

}
