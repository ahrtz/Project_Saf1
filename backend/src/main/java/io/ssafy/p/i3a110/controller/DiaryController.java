package io.ssafy.p.i3a110.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.ssafy.p.i3a110.dto.DiaryDto;
import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.interceptor.Auth;
import io.ssafy.p.i3a110.service.DiaryService;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DiaryController {
    private static final String BE_BASE_URL = "http://i3a110.p.ssafy.io:3000";

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserService userService;

    @Value("${application.staticPath}")
    private String staticPath;

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
    public Object createDiary(
                            HttpSession httpSession,
                            @RequestParam(required = false) MultipartFile file,
                              @RequestParam int uid,
                              @RequestParam String gitName,
                              @RequestParam String title,
                              @RequestParam String intro,
                              @RequestParam String img,
                              @RequestParam String gitUrl,
                              @RequestParam int isProj,
                              @RequestParam Date sDate,
                              @RequestParam Date eDate) throws IOException {
    	String email = (String) httpSession.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	DiaryDto diary = new DiaryDto();
    	diary.setUid(user.getId());
    	diary.setGitName(gitName);
    	diary.setTitle(title);
    	diary.setIntro(intro);
        diary.setGitUrl(gitUrl);
        diary.setIsProj(isProj);
        diary.setSDate(sDate);
        diary.setEDate(eDate);

        if (file != null) {
            long timestamp = System.currentTimeMillis();
            StringBuilder builder = new StringBuilder(staticPath);
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = timestamp + "." + extension;

            file.transferTo(new File(builder.append("/")
                    .append(fileName)
                    .toString()));
            diary.setImg(new StringBuilder(BE_BASE_URL)
                    .append("/diaries/image/")
                    .append(fileName)
                    .toString());
        }

    	System.out.println(diary);
    	diaryService.createDiary(diary);
    	return new ResponseEntity<>(diary.getId(), HttpStatus.OK);
    }

    // Diary 수정
    @Auth
    @PutMapping("/diaries")
    @ApiOperation(value = "다이어리 수정")
    public Object updateDiary(HttpSession httpSession,
                              @RequestParam(required = false) MultipartFile file,
                              @RequestParam int id,
                              @RequestParam String title,
                              @RequestParam String intro,
                              @RequestParam String img,
                              @RequestParam Date sDate,
                              @RequestParam Date eDate
                              ) throws IOException {
    	String email = (String) httpSession.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);

    	if(diaryService.getUidById(id) == user.getId()) {
    	    DiaryDto diary = diaryService.getDiary(String.valueOf(id));
            diary.setTitle(title);
            diary.setIntro(intro);
            diary.setSDate(sDate);
            diary.setEDate(eDate);

            if (file != null) {
                long timestamp = System.currentTimeMillis();
                StringBuilder builder = new StringBuilder(staticPath);
                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                String fileName = timestamp + "." + extension;

                file.transferTo(new File(builder.append("/")
                        .append(fileName)
                        .toString()));
                diary.setImg(new StringBuilder(BE_BASE_URL)
                        .append("/diaries/image/")
                        .append(fileName)
                        .toString());
            }

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

    @GetMapping("/diaries/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(staticPath + "/" + fileName);
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}

