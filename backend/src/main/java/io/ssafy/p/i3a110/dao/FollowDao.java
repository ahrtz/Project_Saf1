package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.FollowDto;
import io.ssafy.p.i3a110.dto.HealthDto;
@Mapper
public interface FollowDao {
	public HealthDto getHealth();

	public List<Integer> getFollower(int uid);

	public List<Integer> getFollowing(int uid);

	public FollowDto checkFollow(int userFrom, int userTo);

	public void updateFollow(FollowDto follow);

	public void createFollow(FollowDto follow);

	public FollowDto getFollw(int userFrom, int userTo);
}
