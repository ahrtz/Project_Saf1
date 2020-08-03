package io.ssafy.p.i3a110.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
public class DiaryController {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserService userService;

    // 전체 Diary 검색 (type - 0: Blog, 1: Project, 2 or Other: All)
    @PostMapping("/diaries/{uid}")
    @ApiOperation(value = "전체 다이어리 조회")
    public List<DiaryDto> getAllDiaries(@PathVariable String uid, @RequestBody HashMap<String, String> map) {
        int isProj = Integer.parseInt(map.get("isProj"));
        String keyword = map.get("keyword");
        
    	return diaryService.getAllDiariesByKeyword(Integer.parseInt(uid), isProj, keyword);
    }

    // Diary 상세 조회
    @GetMapping("/diaries/{id}")
    @ApiOperation(value = "다이어리 상세 조회")
    public DiaryDto getDiary(@PathVariable String id) {
    	return diaryService.getDiary(id);
    }
    
    // Diary 생성
    @Auth
    @PostMapping("/diaries")
    @ApiOperation(value = "다이어리 생성")
    public Object createDiary(HttpSession session, @RequestBody DiaryDto diary) {
    	String email = (String) session.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	diary.setUid(user.getId());
    	
    	diaryService.createDiary(diary);
    	return new ResponseEntity<>(diary.getId(), HttpStatus.OK);
    }

    // Diary 수정
    @Auth
    @PutMapping("/diaries")
    @ApiOperation(value = "다이어리 수정")
    public Object updateDiary(HttpSession session, @RequestBody DiaryDto diary) {
    	String email = (String) session.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	
    	int id = diary.getId();
    	if(diaryService.getUidById(id) == user.getId()) {
    		diaryService.updateDiary(diary);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    // Diary 삭제
    @Auth
    @DeleteMapping("/diaries/{id}")
    @ApiOperation(value = "다이어리 삭제")
    public Object deleteDiary(HttpSession session, @PathVariable String id) {
    	String email = (String) session.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	
    	if(diaryService.getUidById(Integer.parseInt(id)) == user.getId()) {
    		diaryService.deleteDiary(id);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
}

