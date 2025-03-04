package com.login.login.Service;

import com.login.login.Model.User;
import org.springframework.data.domain.Page;

public interface ResidentService {

    Page<User> getResidents(int page, int pageSize);

    Page<User> searchResidents(String name, String number, int page, int pageSize);

    long getTotalResidentCount();

    void addResident(User user);

    boolean existsByNumber(String number);

    User findByNumber(String number);

    void updateResident(User user);

    void deleteResident(String number);
}
