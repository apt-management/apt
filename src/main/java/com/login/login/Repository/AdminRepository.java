package com.login.login.Repository;

import com.login.login.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByNumber(String number);
}
