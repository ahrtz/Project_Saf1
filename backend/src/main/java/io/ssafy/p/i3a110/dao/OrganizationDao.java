package io.ssafy.p.i3a110.dao;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.HealthDto;
@Mapper
public interface OrganizationDao {
	public HealthDto getHealth();
}