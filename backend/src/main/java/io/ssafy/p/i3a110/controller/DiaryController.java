package io.ssafy.p.i3a110.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.service.DiaryService;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpSession;

@RestController
public class DiaryController {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserService userService;

    // 전체 Diary 검색 (type - 0: Blog, 1: Project, 2 or Other: All)
    @PostMapping("/diaries/{uid}")
    @ApiOperation(value = "전체 다이어리 조회")
    public List<DiaryDto> getAllDiaries(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = userService.findUserByEmail(email);

    	return diaryService.getAllDiariesByKeyword(user.getId()+"", Integer.parseInt(map.get("type")), map.get("keyword"));
    }

    // Diary 상세 조회
    @GetMapping("/diaries/{id}")
    @ApiOperation(value = "다이어리 상세 조회")
    public DiaryDto getDiary(@PathVariable String id) {
    	return diaryService.getDiary(id);
    }
    
    // Diary 생성
    @PostMapping("/diaries")
    @ApiOperation(value = "다이어리 생성")
    public void createDiary(@RequestBody DiaryDto diary) {

    	diaryService.createDiary(diary);
    }

    // Diary 수정
    @PutMapping("/diaries")
    @ApiOperation(value = "다이어리 수정")
    public void updateDiary(@RequestBody DiaryDto diary) {
    	diaryService.updateDiary(diary);
    }

}

