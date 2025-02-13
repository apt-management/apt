package com.login.login.Repository;

import com.login.login.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByNumber(String number);

    void deleteByNumber(String number);

}
