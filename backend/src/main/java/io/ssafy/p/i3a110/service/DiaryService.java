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

	public List<DiaryDto> getAllDiariesByKeyword(int uid, int isProj, String keyword) {
		return diaryDao.getAllDiariesByKeyword(uid, isProj, keyword);
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

	public void deleteDiary(String id) {
		diaryDao.deleteDiary(id);
	}
	
	// GitHub API 용
	public List<String> getAllWrittenProjectName(int uid){
		return diaryDao.getAllWrittenProjcetName(uid);
	}

	public int getUidById(int id) {
		return diaryDao.getUidById(id);
	}
}
