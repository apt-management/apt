package com.login.login.Repository;

import com.login.login.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<User, Long> {

    long count();

    Page<User> findByNameContainingAndNumberContaining(String name, String number, org.springframework.data.domain.Pageable pageable);

    boolean existsByNumber(String number);

    Optional<User> findByNumber(String number);

    void deleteByNumber(String number);
}
