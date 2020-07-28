package io.ssafy.p.i3a110.dao;

import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.dto.UserDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryDao {
    public UserDto getUser();

	public List<DiaryDto> getAllBlogsByKeyword(String uid, String keyword);

	public List<DiaryDto> getAllProjectsByKeyword(String uid, String keyword);

	public List<DiaryDto> getAllDiariesByKeyword(String uid, String keyword);

	public DiaryDto getDiary(String id);

	public void createDiary(DiaryDto diary);

	public void updateDiary(DiaryDto diary);
}
