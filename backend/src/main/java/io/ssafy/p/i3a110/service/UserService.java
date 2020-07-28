package io.ssafy.p.i3a110.service;

import io.ssafy.p.i3a110.dao.UserDao;
import io.ssafy.p.i3a110.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserDto getUser() {
        return userDao.getUser();
    }
}
