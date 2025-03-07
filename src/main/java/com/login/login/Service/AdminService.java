package com.login.login.Service;

import com.login.login.Model.Admin;
import com.login.login.Repository.AdminRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository; // Admin 엔티티용 Repository

    private static final Dotenv dotenv = Dotenv.load();
    private static final String ADMIN_NUMBER = dotenv.get("ADMIN_NUMBER");
    private static final String ADMIN_PASSWORD = dotenv.get("ADMIN_PASSWORD");

    public boolean isValidAdmin(String number, String password) {
        return ADMIN_NUMBER.equals(number) && ADMIN_PASSWORD.equals(password);
    }


    public Admin getAdminByNumber(String number) {
        return adminRepository.findByNumber(number);
    }

}
