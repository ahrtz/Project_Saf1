package io.ssafy.p.i3a110.config;

import io.ssafy.p.i3a110.common.SocialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleLoginSuccessHandler googleLoginSuccessHandler;

    @Autowired
    private GoogleLoginFailureHandler googleLoginFailureHandler;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(googleLoginSuccessHandler)
                .failureHandler(googleLoginFailureHandler);
    }
}
