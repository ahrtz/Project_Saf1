package io.ssafy.p.i3a110.dao;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.GroupDto;
@Mapper
public interface GroupDao {
	public GroupDto getGroupInfoByLeader(String lid);

	public GroupDto getGroupInfoById(String id);
	
	public void createGroup(GroupDto groupDto);

	public void updateGroup(String id, GroupDto groupDto);

	public void deleteGroup(String id);

}
