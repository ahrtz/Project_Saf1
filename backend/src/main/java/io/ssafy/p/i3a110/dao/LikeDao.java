package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface LikeDao {
    public int getLikeCnt(int pid);
    public LikeDto getLike(int uid, int pid);
    public void updateLike(LikeDto like);
}
