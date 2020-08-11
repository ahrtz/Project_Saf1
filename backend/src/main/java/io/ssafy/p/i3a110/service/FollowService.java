package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.FollowDao;
import io.ssafy.p.i3a110.dto.FollowDto;

@Service
public class FollowService {
	@Autowired
	private FollowDao followDao;

	public List<Integer> getFollowInfo(int type, int uid) {
		if(type == 0) {
			return followDao.getFollower(uid);
		}else {
			return followDao.getFollowing(uid);
		}
	}

	public void updateFollow(FollowDto follow) {
		FollowDto check = followDao.checkFollow(follow.getUserFrom(), follow.getUserTo());
		if(check!=null) {
			followDao.updateFollow(follow);
		}else {
			followDao.createFollow(follow);
		}
	}

	public FollowDto getFollow(int userFrom, int userTo) {
		return followDao.getFollw(userFrom, userTo);
	}
}
