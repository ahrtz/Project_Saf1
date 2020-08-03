package io.ssafy.p.i3a110.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String email = principal.getAttribute("email");
        log.info("Success");
        response.sendRedirect("/google/success-callback");
        // GET /api/google -> login window
        // 200 /success-callback
        // 403 /failure-callback
    }
}
