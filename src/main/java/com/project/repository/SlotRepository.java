package com.project.repository;

import com.project.model.Slot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SlotRepository extends CrudRepository<Slot, Integer> {
    @Query("select s from Slot s where s.date=?1 and s.doctor_id=?2")
    Slot searchSlotByDateAndDoctorID(String date, int doctor_id);

    @Query("select s from Slot s where s.doctor_id=?1")
    List<Slot> searchSlotByDoctorID(int id);
}


