package io.ssafy.p.i3a110.dao;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.CommitListDto;
@Mapper
public interface CommitDao {
	public CommitListDto getHealth();
}
