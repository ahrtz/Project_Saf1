package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ApiOperation(value = "키워드로 회원 조회")
    public ArrayList<UserDto> findUsers(@RequestBody HashMap<String, String> map) {
        String keyword = map.get("keyword");

        return userService.findUsers(keyword);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "회원 단일 조회")
    public UserDto findUsersById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/users")
    @ApiOperation(value = "회원 정보 수정")
    public void updateUser(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
//        int id = (int) httpSession.getAttribute("id");
        int id = 5;
        UserDto user = userService.findUserById(id);
        user.setPwd(map.get("pwd"));
        user.setNickname(map.get("nickname"));
        user.setGitId(map.get("git_id"));
        user.setGitUrl(map.get("git_url"));
        user.setIntro(map.get("intro"));
        user.setImg(map.get("img"));
        user.setGitToken(map.get("git_token"));
        user.setIsSocial(Integer.parseInt(map.get("is_social")));
        user.setIsCertified(Integer.parseInt(map.get("is_certified")));

        userService.updateUser(user);
    }

    @DeleteMapping("/users")
    @ApiOperation(value = "회원 탈퇴")
    public void deleteById(HttpSession httpSession) {
        int id = (int) httpSession.getAttribute("id");

        userService.deleteById(id);
    }
}
