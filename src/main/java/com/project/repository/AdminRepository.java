package com.project.repository;

import com.project.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String>
{
    @Query("select a from Admin a where a.username=?1 and a.password=?2")
    public Admin checkAdminLogin(String username, String password);

}
