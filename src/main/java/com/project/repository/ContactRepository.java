package com.project.repository;

import com.project.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    @Query("select c from Contact c where c.role=?1")
    List<Contact> getPatientContacts(String role);
}
