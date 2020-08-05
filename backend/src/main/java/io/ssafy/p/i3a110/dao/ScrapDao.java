package io.ssafy.p.i3a110.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.ssafy.p.i3a110.dto.ScrapDto;
@Mapper
public interface ScrapDao {

	public List<ScrapDto> getAllScrapsByUser(int uid);

	public void createScrap(ScrapDto scrapDto);

	public void deleteScrap(int uid, int pid);

	public ScrapDto getScrap(int uid, int pid);

	public void updateScrap(ScrapDto scrapDto);
	
}
