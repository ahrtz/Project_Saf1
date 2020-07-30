package io.ssafy.p.i3a110.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.GroupDao;
import io.ssafy.p.i3a110.dto.GroupDto;

@Service
public class GroupService {
	@Autowired
	private GroupDao groupDao;

	public GroupDto getGroupInfoByLeader(String lid) {
		return groupDao.getGroupInfoByLeader(lid);
	}
	
	public GroupDto getGroupInfoById(String id) {
		return groupDao.getGroupInfoById(id);
	}

	public void createGroup(GroupDto groupDto) {
		groupDao.createGroup(groupDto);
	}

	public void updateGroup(String id, GroupDto groupDto) {
		groupDao.updateGroup(id, groupDto);
	}

	public void deleteGroup(String id) {
		groupDao.deleteGroup(id);
	}
}
