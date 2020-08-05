package io.ssafy.p.i3a110.controller;

import io.ssafy.p.i3a110.config.LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @GetMapping("/google")
    public void loginGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }

    @GetMapping("/github")
    public void loginGithub(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }

}
