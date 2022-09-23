package com.deadline.knunotice.config.oauth;

import com.deadline.knunotice.config.jsonwebtoken.JwtTokenProviderService;
import com.deadline.knunotice.member.Member;
import com.deadline.knunotice.member.MemberAuthentication;
import com.deadline.knunotice.member.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;

    private final JwtTokenProviderService tokenProviderService;

    public OAuth2AuthenticationSuccessHandler(MemberService memberService, JwtTokenProviderService tokenProviderService) {
        this.memberService = memberService;
        this.tokenProviderService = tokenProviderService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        DefaultOAuth2User customUserDetails = (DefaultOAuth2User) authentication.getPrincipal();
        String email = customUserDetails.getAttribute("email");

        Member member = memberService.findMemberByEmail(email);

        MemberAuthentication memberAuthentication = new MemberAuthentication(member);

        String accessToken = tokenProviderService.generateToken(memberAuthentication, 0);
        String refreshToken = tokenProviderService.generateToken(memberAuthentication, 1);

        // OAuth2 로그인을 정상적으로 마친 사용자는 토큰 없는 상태이므로, 특정 URL로 리다이렉션하여 Controller에서 JWT 기반 Access Token과 Refresh Token을 발급 및 Response로 전달한다.
        getRedirectStrategy().sendRedirect(request, response, "/?accessToken="+accessToken + "&refreshToken=" + refreshToken);
    }

}
