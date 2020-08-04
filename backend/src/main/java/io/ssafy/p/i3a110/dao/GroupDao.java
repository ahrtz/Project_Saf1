package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.GroupDto;
import io.ssafy.p.i3a110.dto.GroupRelationDto;
@Mapper
public interface GroupDao {
	public List<GroupDto> getGroupInfoByUser(String uid);

	public GroupDto getGroupInfoById(String id);
	
	public void createGroup(GroupDto groupDto);

	public void updateGroup(String id, GroupDto groupDto);

	public void deleteGroup(String id);

	public List<String> getUserListById(String id);

	public void inviteGroup(GroupRelationDto groupRelationDto);
}
