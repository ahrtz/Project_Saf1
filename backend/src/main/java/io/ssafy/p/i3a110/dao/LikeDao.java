package io.ssafy.p.i3a110.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.LikeDto;

@Mapper
public interface LikeDao {
    public LikeDto getLike(int uid, int pid);
    public void updateLike(LikeDto like);
	public void makeLike(LikeDto like);
	public List<HashMap<Object, Object>> getLikeCntByUser();
	public List<HashMap<Object, Object>> getLikeCntByDiary();
}
