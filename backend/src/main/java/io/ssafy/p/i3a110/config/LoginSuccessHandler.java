package io.ssafy.p.i3a110.config;

import io.ssafy.p.i3a110.dao.UserDao;
import io.ssafy.p.i3a110.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDao userDao;

    /**
     * GET /api/google -> login window
     * 200 /success-callback
     * 403 /failure-callback
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getPrincipal().getClass());
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();


        String email = principal.getAttribute("email");

        if (email == null || "".equals(email)) {
            email = principal.getAttribute("id") + "@github.com";
        }

        UserDto user = userDao.findUserByEmail(email);

        System.out.println(principal.toString());

        if (user == null) {
            user = new UserDto();
            user.setEmail(email);
            user.setIsSocial(1);
            user.setNickname("Anonymous");
            userDao.insertUser(user);
        }

        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("isLoggedIn", true);
        response.sendRedirect("/social/success-callback");  // 301
    }
}
