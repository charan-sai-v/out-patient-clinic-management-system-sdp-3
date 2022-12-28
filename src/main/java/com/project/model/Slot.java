package com.project.model;

import javax.persistence.*;

@Entity
@Table(name = "slot_table")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    int doctor_id;
    @Column
    String date;
    @Column()
    int slot_1;
    @Column()
    int slot_2;
    @Column()
    int slot_3;
    @Column()
    int slot_4;
    @Column()
    int slot_5;
    @Column()
    int slot_6;
    @Column()
    int slot_7;
    @Column()
    int slot_8;
    @Column()
    int slot_9;
    @Column()
    int slot_10;
    @Column()
    int slot_11;
    @Column()
    int slot_12;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlot_1() {
        return slot_1;
    }

    public void setSlot_1(int slot_1) {
        this.slot_1 = slot_1;
    }

    public int getSlot_2() {
        return slot_2;
    }

    public void setSlot_2(int slot_2) {
        this.slot_2 = slot_2;
    }

    public int getSlot_3() {
        return slot_3;
    }

    public void setSlot_3(int slot_3) {
        this.slot_3 = slot_3;
    }

    public int getSlot_4() {
        return slot_4;
    }

    public void setSlot_4(int slot_4) {
        this.slot_4 = slot_4;
    }

    public int getSlot_5() {
        return slot_5;
    }

    public void setSlot_5(int slot_5) {
        this.slot_5 = slot_5;
    }

    public int getSlot_6() {
        return slot_6;
    }

    public void setSlot_6(int slot_6) {
        this.slot_6 = slot_6;
    }

    public int getSlot_7() {
        return slot_7;
    }

    public void setSlot_7(int slot_7) {
        this.slot_7 = slot_7;
    }

    public int getSlot_8() {
        return slot_8;
    }

    public void setSlot_8(int slot_8) {
        this.slot_8 = slot_8;
    }

    public int getSlot_9() {
        return slot_9;
    }

    public void setSlot_9(int slot_9) {
        this.slot_9 = slot_9;
    }

    public int getSlot_10() {
        return slot_10;
    }

    public void setSlot_10(int slot_10) {
        this.slot_10 = slot_10;
    }

    public int getSlot_11() {
        return slot_11;
    }

    public void setSlot_11(int slot_11) {
        this.slot_11 = slot_11;
    }

    public int getSlot_12() {
        return slot_12;
    }

    public void setSlot_12(int slot_12) {
        this.slot_12 = slot_12;
    }
}