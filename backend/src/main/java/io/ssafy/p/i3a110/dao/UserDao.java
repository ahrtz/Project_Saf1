package io.ssafy.p.i3a110.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.UserDto;

@Mapper
public interface UserDao {
    public ArrayList<UserDto> findUsers(String keyword);
    public UserDto findUserById(int id);
    public UserDto findUserByEmail(String email);
    public void insertUser(UserDto user);
    public void updateUser(UserDto user);
    public void deleteById(int id);
	public void cancelToken(int id);
	public void authenticateToken(int id);
	public List<UserDto> getAllGitUsers();
	public List<UserDto> getAllUsers();
}
