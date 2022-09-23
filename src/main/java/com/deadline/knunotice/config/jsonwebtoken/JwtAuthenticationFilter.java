package com.deadline.knunotice.config.jsonwebtoken;

import com.deadline.knunotice.member.Member;
import com.deadline.knunotice.member.MemberAuthentication;
import com.deadline.knunotice.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    private final JwtTokenProviderService jwtTokenProviderService;

    public JwtAuthenticationFilter(MemberService memberService, JwtTokenProviderService jwtTokenProviderService) {
        this.memberService = memberService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.
            if (StringUtils.isNotEmpty(jwt) && jwtTokenProviderService.validateToken(jwt, 0)) {
                String email = jwtTokenProviderService.getUserIdFromJWT(jwt, 0); //jwt에서 사용자 id를 꺼낸다.

                Member member = memberService.findMemberByEmail(email);
                MemberAuthentication authentication = new MemberAuthentication(member);
                authentication.setAuthenticated(true);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
            } else {
                if (StringUtils.isEmpty(jwt)) {
                    request.setAttribute("unauthorization", "401 인증키 없음.");
                }

                if (jwtTokenProviderService.validateToken(jwt, 0)) {
                    request.setAttribute("unauthorization", "401-001 인증키 만료.");
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

}
