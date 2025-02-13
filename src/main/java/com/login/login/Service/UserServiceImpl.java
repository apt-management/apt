package com.login.login.Service;

import com.login.login.Dao.UserDao;
import com.login.login.Model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao dao;

    @Override
    public boolean login(User user) {
        User item = dao.findByNumberAndPassword(user.getNumber(), user.getPassword());
        if (item != null) {
            user.setNumber(item.getNumber());
            user.setUserid(item.getUserid());
            user.setPassword(item.getPassword());
            user.setName(item.getName());
            user.setAddress(item.getAddress());
            return true;
        }
        return false;
    }

    @Override
    public boolean checkDuplicateNumber(String number) {
        return dao.existsByNumber(number);
    }

    @Override
    public boolean signup(User user) {
        if (checkDuplicateNumber(user.getNumber())) {
            return false;
        }
        dao.save(user);
        return true;
    }

    @Override
    public User findByNumber(String number) {
        return dao.findByNumber(number);
    }

    @Transactional
    @Override
    public void deleteUser(String number) {
        dao.deleteByNumber(number);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        dao.updateUser(user.getNumber(), user.getPassword(), user.getName(), user.getAddress());
    }

}
