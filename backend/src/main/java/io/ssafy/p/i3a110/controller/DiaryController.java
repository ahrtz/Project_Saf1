package io.ssafy.p.i3a110.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;

import org.apache.ibatis.annotations.Param;
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
    @PostMapping("/diaries")
    @ApiOperation(value = "다이어리 생성")
    public void createDiary(@RequestBody DiaryDto diary) {
    	System.out.println(diary.toString());
    	System.out.println(diaryService.createDiary(diary));
    }

    // Diary 수정
    @PutMapping("/diaries")
    @ApiOperation(value = "다이어리 수정")
    public void updateDiary(@RequestBody DiaryDto diary) {
    	diaryService.updateDiary(diary);
    }
    
    // Diary 삭제
    @DeleteMapping("/diaries/{id}")
    @ApiOperation(value = "다이어리 삭제")
    public void deleteDiary(@PathVariable String id) {
    	diaryService.deleteDiary(id);
    }

}

