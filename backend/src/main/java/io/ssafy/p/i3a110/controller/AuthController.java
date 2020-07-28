package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.dto.UserDto;
import io.ssafy.p.i3a110.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    @ApiOperation(value = "로그인")
    public void login(HttpSession httpSession, @RequestBody HashMap<String, String> map) {
        String email = map.get("email");
        String pwd = map.get("pwd");
        UserDto user = userService.findUserByEmail(email);
        if(pwd.equals(user.getPwd())) {
            httpSession.setAttribute("isLoggedIn", true);
            httpSession.setAttribute("email", email);
        }
    }

    @PostMapping("/auth/logout")
    @ApiOperation(value = "로그아웃")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @GetMapping("/auth/is-logged-in")
    @ApiOperation(value = "로그인 체크")
    public boolean isLoggedIn(HttpSession httpSession) {
        boolean result = (boolean)httpSession.getAttribute("isLoggedIn");
        return result;
    }

    @PostMapping("/auth/signup")
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
}
