package com.deadline.knunotice.config;

import com.deadline.knunotice.config.authentication.AuthenticationProviderService;
import com.deadline.knunotice.config.jsonwebtoken.JwtAuthenticationFilter;
import com.deadline.knunotice.config.jsonwebtoken.JwtTokenProviderService;
import com.deadline.knunotice.config.oauth.OAuth2AuthenticationSuccessHandler;
import com.deadline.knunotice.config.oauth.CustomOauth2UserService;
import com.deadline.knunotice.member.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;

    private final AuthenticationProviderService authenticationProvider;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final MemberService memberService;

    private final JwtTokenProviderService jwtTokenProviderService;

    public SecurityConfig(CustomOauth2UserService customOauth2UserService, AuthenticationProviderService authenticationProvider, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, MemberService memberService, JwtTokenProviderService jwtTokenProviderService) {
        this.customOauth2UserService = customOauth2UserService;
        this.authenticationProvider = authenticationProvider;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.memberService = memberService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/member/signUp").permitAll()
                .antMatchers("/api/member/validate").permitAll()
                .antMatchers("/api/notice").permitAll()
                .antMatchers("/api/notice/search").permitAll()
                .antMatchers("/api/college").permitAll()
                .antMatchers("/api/major").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .permitAll()
            .and()
                .oauth2Login()
                .permitAll()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(memberService, jwtTokenProviderService), UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProvider);
        return http.build();
    }

}
