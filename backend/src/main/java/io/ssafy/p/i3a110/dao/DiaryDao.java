package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.dto.UserDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryDao {
    public UserDto getUser();

	public List<DiaryDto> getAllDiariesByKeyword(int uid, int isProj, String keyword);

	public DiaryDto getDiary(String id);

	public int createDiary(DiaryDto diary);

	public void updateDiary(DiaryDto diary);

	public void deleteDiary(String id);
	
	public List<String> getAllWrittenProjcetName(int uid);

	public int getUidById(int id);

}
