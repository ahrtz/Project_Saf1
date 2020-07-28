package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public UserDto getUser();
}
