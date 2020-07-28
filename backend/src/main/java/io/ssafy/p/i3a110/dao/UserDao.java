package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserDao {
    public ArrayList<UserDto> findUsers(String keyword);
    public UserDto findUserById(int id);
    public UserDto findUserByEmail(String email);
    public void insertUser(UserDto user);
    public void updateUser(UserDto user);
    public void deleteById(int id);
}
