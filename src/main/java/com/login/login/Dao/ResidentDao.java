package com.login.login.Dao;

import com.login.login.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentDao extends JpaRepository<User, Integer> {

    Page<User> findByNameAndNumber(String name, String number, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    long count();

    long countByNameAndNumber(String name, String number);

}
