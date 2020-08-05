package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UserController {

//    private static final String BE_BASE_URL = "http://i3p110.p.ssafy.io:3000";

    @Autowired
    private UserService userService;

//    @Value("${application.staticPath}")
//    private String staticPath;

    @PostMapping("/users")
    @ApiOperation(value = "키워드로 회원 조회")
    public ArrayList<UserDto> findUsers(@RequestBody HashMap<String, String> map) {
        String keyword = map.get("keyword");

        return userService.findUsers(keyword);
    }
    
    @GetMapping("/users/{email}")
    @ApiOperation(value = "회원 단일 조회")
    public UserDto findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
    
    @PutMapping("/users")
    @ApiOperation(value = "회원 정보 수정")
    public void updateUser(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
    	String email = (String) httpSession.getAttribute("email");
    	UserDto user = userService.findUserByEmail(email);
    	user.setPwd(map.get("pwd"));
    	user.setNickname(map.get("nickname"));
    	user.setGitId(map.get("gitId"));
    	user.setGitUrl(map.get("gitUrl"));
    	user.setIntro(map.get("intro"));
    	user.setImg(map.get("img"));
    	user.setGitToken(map.get("gitToken"));
    	user.setIsSocial(Integer.parseInt(map.get("isSocial")));
    	user.setIsCertified(Integer.parseInt(map.get("isCertified")));
    	
    	userService.updateUser(user);
    }

    @DeleteMapping("/users")
    @ApiOperation(value = "회원 탈퇴")
    public void deleteById(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = findUserByEmail(email);

        userService.deleteById(user.getId());
    }

    @PostMapping("/users/login")
    @ApiOperation(value = "로그인")
    public Object login(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = map.get("email");
        String pwd = map.get("pwd");
        UserDto user = userService.findUserByEmail(email);
        if(user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(pwd.equals(user.getPwd())) {
            httpSession.setAttribute("isLoggedIn", true);
            httpSession.setAttribute("email", email);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/logout")
    @ApiOperation(value = "로그아웃")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @GetMapping("/users/is-logged-in")
    @ApiOperation(value = "로그인 체크")
    public boolean isLoggedIn(HttpSession httpSession) {
        boolean result;
        if(httpSession.getAttribute("isLoggedIn") == null) {
            result = false;
        } else {
            result = (boolean) httpSession.getAttribute("isLoggedIn");
        }
        return result;
    }
    
    @GetMapping("/users/me")
    @ApiOperation(value = "내 정보 조회")
    public UserDto me(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = findUserByEmail(email);
        return user;
    }

    @PostMapping("/users/signup")
    @ApiOperation(value = "회원 가입")
//    public void signup(@RequestBody HashMap<String, String> map, @RequestParam(required = false) MultipartFile file) throws Exception {
    public void signup(@RequestBody HashMap<String, String> map) {
        UserDto user = userService.findUserByEmail(map.get("email"));
        if(user == null) {
            user = new UserDto();
            user.setEmail(map.get("email"));
            user.setPwd(map.get("pwd"));
            user.setNickname(map.get("nickname"));
            user.setGitId(map.get("gitId"));
            user.setGitUrl(map.get("gitUrl"));
            user.setIntro(map.get("intro"));
            user.setImg(map.get("img"));
            user.setGitToken(map.get("gitToken"));
            user.setIsSocial(Integer.parseInt(map.get("isSocial")));
            user.setIsCertified(Integer.parseInt(map.get("isCertified")));

            // image url과는 별개로 "file" multipart로 받기
            // 이미지 업데이트
//            if (file == null) {
//                user.setImg(null);
//            } else {
//                long timestamp = System.currentTimeMillis();
//                StringBuilder builder = new StringBuilder(staticPath);
//                String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//                String fileName = timestamp + "." + extension;
//
//                file.transferTo(new File(builder.append("/")
//                        .append(fileName)
//                        .toString()));
//                user.setImg(new StringBuilder(BE_BASE_URL)
//                        .append("/users/image/")
//                        .append(fileName)
//                        .toString());
//            }

            userService.insertUser(user);
        }

    }

//    @GetMapping("/users/image/{fileName}")
//    public ResponseEntity<Resource> getImage(
//            @PathVariable String fileName
//    ) throws IOException {
//        Path path = Paths.get(staticPath + "/" + fileName);
//        String contentType = Files.probeContentType(path);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
//
//        Resource resource = new InputStreamResource(Files.newInputStream(path));
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//    }
}
