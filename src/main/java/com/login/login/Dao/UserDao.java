package com.login.login.Dao;

import com.login.login.Model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByNumberAndPassword(String number, String password);

    User save(User item);

    User findByNumber(String number);

    void deleteByNumber(String number);

    boolean existsByNumber(String number);

    @Modifying
    @Query("UPDATE User u SET u.password = :password, u.name = :name, u.address = :address WHERE u.number = :number")
    int updateUser(@Param("number") String number, @Param("password") String password, @Param("name") String name, @Param("address") String address);

}
