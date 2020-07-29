package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/users/{email}")
    @ApiOperation(value = "회원 단일 조회")
    public UserDto findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PutMapping("/users")
    @ApiOperation(value = "회원 정보 수정")
    public void updateUser(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        int id = (int) httpSession.getAttribute("id");
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

    @PostMapping("/users/login")
    @ApiOperation(value = "로그인")
    public void login(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = map.get("email");
        String pwd = map.get("pwd");
        UserDto user = userService.findUserByEmail(email);
        System.out.println(pwd);
        System.out.println(user.getPwd());
        if(pwd.equals(user.getPwd())) {
            httpSession.setAttribute("isLoggedIn", true);
            httpSession.setAttribute("id", user.getId());
            httpSession.setAttribute("email", email);
            System.out.println(httpSession.getAttribute("email"));
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

    @PostMapping("/users/signup")
    @ApiOperation(value = "가입하기")
    public void signup(@RequestBody HashMap<String, String> map) {
        UserDto user = userService.findUserByEmail(map.get("email"));
        if(user == null) {
            user = new UserDto();
            user.setEmail(map.get("email"));
            user.setPwd(map.get("pwd"));
            user.setNickname(map.get("nickname"));
            user.setGitId(map.get("git_id"));
            user.setGitUrl(map.get("git_url"));
            user.setIntro(map.get("intro"));
            user.setImg(map.get("img"));
            user.setGitToken(map.get("git_token"));
            user.setIsSocial(Integer.parseInt(map.get("is_social")));
            user.setIsCertified(Integer.parseInt(map.get("is_certified")));

            userService.insertUser(user);
        }

    }

    @GetMapping("/users/me")
    @ApiOperation(value = "내 정보 조회")
    public UserDto me(HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        UserDto user = findUserByEmail(email);

        return user;
    }
}
