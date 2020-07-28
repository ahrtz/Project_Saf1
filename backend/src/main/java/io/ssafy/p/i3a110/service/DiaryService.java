package io.ssafy.p.i3a110.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ssafy.p.i3a110.dao.DiaryDao;
import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.dto.UserDto;

@Service
public class DiaryService {
    @Autowired
    private DiaryDao diaryDao;

    public UserDto getUser() {
        return diaryDao.getUser();
    }

	public List<DiaryDto> getAllDiariesByKeyword(String uid, int type, String keyword) {
		switch (type) {
		case 0:
			return diaryDao.getAllBlogsByKeyword(uid, keyword);
		case 1:
			return diaryDao.getAllProjectsByKeyword(uid, keyword);
		default:
			return diaryDao.getAllDiariesByKeyword(uid, keyword);
		}
	}

	public DiaryDto getDiary(String id) {
		return diaryDao.getDiary(id);
	}

	public void createDiary(DiaryDto diary) {
		diaryDao.createDiary(diary);
	}

	public void updateDiary(DiaryDto diary) {
		diaryDao.updateDiary(diary);
	}

}
