package io.ssafy.p.i3a110.dao;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.RateDto;
@Mapper
public interface RateDao {

	public RateDto getRateByUid(int uid);

	public void createOdocOfAllUsers(RateDto rate);

	public void updateOdocOfAllUsers(RateDto rate);

	public void createOdopOfAllUsers(RateDto rate);

	public void updateOdopOfAllUsers(RateDto rate);
}
