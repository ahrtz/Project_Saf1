package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.UserDao;
import io.ssafy.p.i3a110.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public ArrayList<UserDto> findUsers(String keyword) {
        return userDao.findUsers(keyword);
    }

    public UserDto findUserById(int id) {
        return userDao.findUserById(id);
    }

    public void updateUser(UserDto user) {
        userDao.updateUser(user);
    }

    public void deleteById(int id) {
        userDao.deleteById(id);
    }


}
