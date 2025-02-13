package com.login.login.Service;

import com.login.login.Model.User;

public interface UserService {

    boolean login(User user);

    boolean signup(User user);

    User findByNumber(String number);

    void deleteUser(String number);

    void updateUser(User user);

    boolean checkDuplicateNumber(String number);

}
