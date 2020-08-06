package io.ssafy.p.i3a110.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.GroupDao;
import io.ssafy.p.i3a110.dto.GroupDto;
import io.ssafy.p.i3a110.dto.GroupRelationDto;

@Service
public class GroupService {
	@Autowired
	private GroupDao groupDao;

	public List<GroupDto> getGroupInfoByUser(String uid) {
		return groupDao.getGroupInfoByUser(uid);
	}
	
	public GroupDto getGroupInfoById(String id) {
		return groupDao.getGroupInfoById(id);
	}

	public void createGroup(GroupDto groupDto) {
		groupDao.createGroup(groupDto);
		GroupRelationDto groupRealtionDto = new GroupRelationDto();
		groupRealtionDto.setOid(groupDto.getId());
		groupRealtionDto.setUid(groupDto.getLid());
		groupDao.inviteGroup(groupRealtionDto);
	}

	public void updateGroup(GroupDto groupDto) {
		groupDao.updateGroup(groupDto);
	}

	public void deleteGroup(String id) {
		groupDao.deleteGroup(id);
	}

	public List<String> getUserListById(String id) {
		return groupDao.getUserListById(id);
	}

	public void inviteGroup(GroupRelationDto groupRelationDto) {
		groupDao.inviteGroup(groupRelationDto);
	}

	public void withdrawGroup(GroupRelationDto groupRelationDto) {
		groupDao.withdrawGroup(groupRelationDto);
	}

	public GroupRelationDto checkMember(String id, int uid) {
		return groupDao.checkMember(id, uid);
	}

	public int getMemberCntById(int id) {
		return groupDao.getMemberCntById(id);
	}

	public List<HashMap<String, String>> getTopNUserByType(int oid, int type, int cnt) {
		if(type == 0) {
			return groupDao.getTopNUserByLike(oid, cnt);
		}else {
			return groupDao.getTopNUserByPost(oid, cnt);
		}
	}
}
