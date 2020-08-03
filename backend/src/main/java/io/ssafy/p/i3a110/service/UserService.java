package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.UserDao;
import io.ssafy.p.i3a110.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public ArrayList<UserDto> findUsers(String keyword) {
        return userDao.findUsers(keyword);
    }

    public UserDto findUserById(int id) {
        return userDao.findUserById(id);
    }

    public UserDto findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public void insertUser(UserDto user) {
        userDao.insertUser(user);
    }

    public void updateUser(UserDto user) {
        userDao.updateUser(user);
    }

    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = userDao.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User is not registered.");
        }

        return new User(email, user.getPwd(), Collections.emptyList());
    }
}
